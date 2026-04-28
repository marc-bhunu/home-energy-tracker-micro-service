# Home Energy Tracker - AI Agent Guide

## Architecture Overview

This is a **microservices energy tracking platform** with event-driven cross-service communication:

- **API Gateway** (port 9000): Spring Cloud Gateway with Resilience4j circuit breakers routing to backend services
- **User Service** (port 8080): User CRUD & authentication, Flyway-managed MySQL database
- **Device Service**: Device registration & management (MySQL)
- **Alert Service** (port 8084): Kafka consumer listening for `AlertingEvent` messages, triggers email alerts via Mailpit
- **Ingestion Service** (port 8082): Kafka producer generating energy usage events from simulations (configurable interval & parallelism)
- **Usage Service**: Kafka consumer writing time-series data to InfluxDB (retains 1 week)
- **Insights Service**: Spring AI integration with Ollama for ML-based energy insights

## Critical Workflows

### Local Development Setup
```bash
# Start infrastructure (MySQL, Kafka, InfluxDB, Mailpit, Kafka-UI)
docker compose -v up -d

# Access services
# - API Gateway: http://localhost:9000
# - Kafka UI: http://localhost:8070
# - Mailpit Web: http://localhost:8025 (SMTP on :1025)
# - InfluxDB: http://localhost:8072
```

### Building & Running Individual Services
Each service is a standard Maven Spring Boot project in its own directory:
```bash
cd <service-name>
./mvnw clean package  # Build JAR with spring-boot-maven-plugin
java -jar target/*.jar  # Run individual service
```

### Kafka Configuration
- **Bootstrap servers**: `localhost:9094` (host access), `kafka:9092` (Docker network)
- **Topics**: `energy-usage` (producer: ingestion-service), `alerting-events` (consumer: alert-service)
- **Format**: Spring-managed JSON serialization with type mapping (e.g., `AlertingEvent`)
- **KRaft mode**: Single-node cluster, auto-topic creation enabled
- **Kafka UI**: Free debugging tool at port 8070

### Database Strategy
- **MySQL** (single shared instance): Transactional data (users, devices, alerts) via Spring Data JPA
- **InfluxDB**: Time-series energy readings with 1-week retention, accessed via influxdb-client-java
- **Flyway**: Used in user-service for schema versioning (see `spring.jpa.hibernate.ddl-auto=none`)

## Project-Specific Conventions

### Package Structure & Naming
All services follow: `com.marcuswhocodes.<service_name_with_underscores>`
```
user-service/src/main/java/com/marcuswhocodes/user_service/
├── controller/       # REST endpoints
├── service/          # Business logic (interfaces in parent, impl/ subfolder)
├── domain/           # JPA entities
├── repository/       # Spring Data JPA repositories (note: typo in user-service "repositorty")
├── aspects/          # AOP cross-cutting concerns (ExecutionTimeAspect, LoggingAspect)
├── exceptions/       # Custom exceptions
└── UserServiceApplication.java  # @SpringBootApplication entry point
```

### Configuration Patterns
- **application.properties**: Service-specific config (port, DB credentials, Kafka bootstrap servers)
- **Lombok**: Configured with Maven compiler plugin annotation processors (all services)
- **Spring Profiles**: Not visible in current config; can be added for environment-specific settings
- **Credentials**: Hardcoded in properties (MySQL: root/password, Influx: admin/admin123, Kafka: no auth)

### Kafka Event Patterns
- **Producers** use `spring.kafka.template.default-topic` and `JsonSerializer`
- **Consumers** use `JsonDeserializer` with explicit type mapping: `spring.kafka.consumer.properties.spring.json.type.mapping=eventClassName:com.fully.qualified.ClassName`
- **Cross-service event contracts**: Located in `kafka/event/` package (e.g., `AlertingEvent`)

### Testing Infrastructure
Spring Boot test starters configured per service type:
- `spring-boot-starter-*-test` (kafka-test, webmvc-test, data-jpa-test)
- Tests run via Maven's surefire plugin; results in `target/surefire-reports/`

### AOP & Aspects
User Service demonstrates custom aspects for monitoring:
```java
// ExecutionTimeAspect.java - Measures method execution time
// LoggingAspect.java - Cross-service logging
// Configure via @EnableAspectJAutoProxy annotation
```

## Integration & Data Flow

### Core Data Pipelines
1. **Ingestion → Kafka → Usage Service → InfluxDB** (energy readings)
2. **Alert Service Kafka Consumer → EmailService → Mailpit SMTP** (user notifications)
3. **All Services → API Gateway** (HTTP routing with circuit breaker fallback)

### Cross-Service Communication Patterns
- **Sync**: HTTP via API Gateway (e.g., user lookup via REST)
- **Async**: Kafka events (decoupled, resilient, high-throughput)
- **Service Discovery**: Hardcoded URLs in application.properties (not Eureka/Consul)

### Email Alerting Detail
- Alert Service consumes `AlertingEvent` messages; `EmailService` sends via Mailpit SMTP (dev) 
- Mailpit UI shows all sent emails; no actual mail delivery (testing only)
- Configuration: `spring.mail.host=localhost:1025`

## Development Tips

### Lombok Boilerplate
Services use `@Data`, `@Slf4j`, `@AllArgsConstructor` to reduce entity/service verbosity
- Ensure IDE has Lombok plugin installed for annotation processor support

### Simulating Energy Data
Ingestion Service generates synthetic data based on `application.properties`:
```properties
simulation.interval-ms=6000          # Generate batch every 6 seconds
simulation.requests-per-interval=1000 # 1000 readings per batch
simulation.parallel-threads=10       # Parallel thread pool size
```

### Circuit Breaker Monitoring
API Gateway exposes health & metrics at `http://localhost:9000/actuator/health`
Resilience4j settings: 20% failure threshold, 8-call sliding window, 5s open-state duration

### Spring Boot Version Variance
- Most services: Spring Boot 4.0.5
- API Gateway: Spring Boot 4.0.6
- Insights Service: Spring Boot 3.5.13 (for Spring AI compatibility)
- **Always check individual pom.xml before assuming version consistency**

## File Reference

- **Services root**: Each service has own directory with `pom.xml`, `src/`, `target/`
- **Docker Compose**: `docker-compose.yml` (orchestrates all infrastructure)
- **Database migrations**: User Service `src/main/resources/db/migration/*` (Flyway)
- **Init SQL**: `docker/mysql/init.sql` (creates `home_energy_tracker` DB)
- **Kafka UI**: `http://localhost:8070` (no auth, view topics/consumers/messages)

## Common Pitfalls

1. **Port conflicts**: Each service has hardcoded ports—ensure no local services on 8080-8084, 9000
2. **MySQL connectivity**: Services expect `home_energy_tracker` DB; if init.sql doesn't run, create manually
3. **Kafka bootstrap**: Use `localhost:9094` from host (external), `kafka:9092` inside Docker compose
4. **Flyway migrations**: Only user-service has flyway; others use `ddl-auto=none` (manual schema management)
5. **Type mapping mismatch**: Kafka consumers fail if `spring.json.type.mapping` doesn't match producer event class names

