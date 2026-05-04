# Home Energy Tracker

## Overview

**Home Energy Tracker** is a cloud-native microservices platform for monitoring, analyzing, and optimizing residential energy consumption. The system ingests simulated or real-time energy usage data from smart meters, processes it through event-driven Kafka pipelines, stores time-series metrics in InfluxDB, and generates AI-powered insights about energy patterns and efficiency recommendations.

### Key Features

- **Real-time Energy Monitoring**: Ingest and track energy consumption data in real-time from multiple devices
- **Event-Driven Architecture**: Asynchronous Kafka-based communication for decoupled, scalable services
- **Time-Series Analytics**: Persistent energy usage data stored in InfluxDB with configurable retention
- **Intelligent Alerting**: Multi-tier alerting system that triggers email notifications when users exceed energy thresholds
- **AI-Powered Insights**: Spring AI integration with Ollama for machine learning-based energy recommendations
- **Centralized User Management**: User authentication, device registration, and alert configuration
- **Comprehensive Monitoring**: Grafana dashboards for visualizing energy trends and system health
- **Keycloak Integration**: Secure OAuth2/OpenID Connect authentication

### Architecture

```
                        API Gateway (port 9000)
                                ↓
                ┌────────────────────────────────────┐
                │      Microservices                 │
                ├────────────────────────────────────┤
                │ • User Service (8080)              │
                │ • Device Service                   │
                │ • Ingestion Service (8082)         │
                │ • Usage Service                    │
                │ • Alert Service (8084)             │
                │ • Insights Service                 │
                └────────────────────────────────────┘
                                ↓
                ┌────────────────────────────────────┐
                │      Data Layer                    │
                ├────────────────────────────────────┤
                │ • MySQL       - Transactional      │
                │ • Kafka       - Event Streaming    │
                │ • InfluxDB    - Time-Series        │
                │ • Keycloak    - Identity Mgmt      │
                └────────────────────────────────────┘
```

**Data Flow**: Energy devices generate usage → Ingestion Service produces Kafka events → Usage Service consumes & stores to InfluxDB → Alert Service monitors thresholds → Alerts trigger email via Mailpit → Insights Service generates recommendations → Grafana visualizes metrics

---

## Getting Started

### Prerequisites

- Docker & Docker Compose installed
- Maven 3.8+ (for building services locally)
- Java 17+ (for running individual services)

### How to Start

1. **Start all infrastructure services** (MySQL, Kafka, InfluxDB, Keycloak, Grafana, Prometheus, Mailpit):

```bash
docker compose -v up -d
```

This will start:
- **API Gateway**: http://localhost:9000
- **Kafka UI**: http://localhost:8070 (view topics, messages, consumers)
- **Mailpit Web**: http://localhost:8025 (view sent emails)
- **InfluxDB**: http://localhost:8072
- **Keycloak**: http://localhost:8091 (admin: admin/admin)
- **Grafana**: http://localhost:3000 (admin: admin/admin)
- **Prometheus**: http://localhost:9090 (metrics scraper)

2. **Build and run individual services** (from project root or service directory):

```bash
# Build all services
mvn clean install

# Or build a specific service
cd user-service && ./mvnw clean package && java -jar target/*.jar
```

3. **Stop the project**:

```bash
docker compose down
```

### Troubleshooting

- **Database connection issues**: Delete pre-existing MySQL volumes if database state is corrupted:
  ```bash
  docker compose down -v
  docker compose -v up -d
  ```
- **Kafka topics missing**: Auto-topic creation is enabled—topics will be created on first producer message
- **Keycloak realm import fails**: Check `docker/keycloak/realms/` for valid JSON realm configuration files

---

## Technology Stack

| Component | Purpose | Version |
|-----------|---------|---------|
| Spring Boot | Microservices framework | 4.0.5 (most), 3.5.13 (Insights) |
| Spring Cloud Gateway | API routing & circuit breaker | Latest |
| Apache Kafka | Event streaming | KRaft mode, single-node |
| MySQL | Transactional database | 8.3.0 |
| InfluxDB | Time-series database | 2.7 |
| Keycloak | Identity provider | 24.0.1 |
| Grafana | Metrics visualization | 11.4.0 |
| Prometheus | Metrics collection | 3.1.0 |
| Spring Data JPA | ORM layer | Latest |
| Flyway | Database migrations | Latest |
| Testcontainers | Integration testing | Latest |

---

## Project Structure

```
home-energy-tracker/
├── api-gateway/              # Spring Cloud Gateway (port 9000)
├── user-service/             # User CRUD & authentication (port 8080)
├── device-service/           # Device management
├── ingestion-service/        # Energy event producer (port 8082)
├── usage-service/            # Kafka consumer → InfluxDB
├── alert-service/            # Alert consumer → Email (port 8084)
├── insights-service/         # ML-powered recommendations
├── docker/                   # Infrastructure configuration
│   ├── mysql/init.sql        # Database initialization
│   ├── keycloak/realms/      # Keycloak realm imports
│   ├── prometheus/           # Metrics configuration
│   └── grafana/              # Grafana provisioning
├── docker-compose.yml        # Orchestration
└── AGENTS.md                 # AI agent coding guide
```

---

## Configuration

Each service has its own `application.properties` in `src/main/resources/`:

- **Kafka Bootstrap**: `localhost:9094` (external), `kafka:9092` (Docker)
- **MySQL**: `localhost:3306`, credentials: `root/password`
- **InfluxDB**: `localhost:8072`, credentials: `admin/admin123`
- **Keycloak**: `http://localhost:8091`, admin: `admin/admin`

See [AGENTS.md](./AGENTS.md) for detailed service configuration and conventions.
