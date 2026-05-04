package com.marcuswhocodes.user_service.testsupport;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.mysql.MySQLContainer;

public abstract class MySqlTestContainerBase {


    @ServiceConnection
    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.4.0")
            .withDatabaseName("home_energy_tracker")
            .withUsername("root")
            .withPassword("password");
}
