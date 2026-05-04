package com.marcuswhocodes.user_service.integration;


import com.marcuswhocodes.user_service.domain.dto.UserDto;
import com.marcuswhocodes.user_service.repositorty.UserRepository;
import com.marcuswhocodes.user_service.testsupport.MySqlTestContainerBase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

//@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestRestTemplate
@ActiveProfiles("test")
public class UserServiceIntegrationTest extends MySqlTestContainerBase {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;


    @Test
    void createdUser_viaRestApi_persistsAndReturnsUser(){

        UserDto request =UserDto.builder()
                .name("Marcus")
                .surname("Marcus Surname")
                .email("marcus@marcus.com")
                .address("Marcus Address")
                .alerting(true)
                .energyAlertingThreshold(2000.0)
                .build();

        ResponseEntity<UserDto> response = restTemplate.postForEntity(
                        "/api/v1/user",
                        request,
                        UserDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Marcus");
        assertThat(response.getBody().getSurname()).isEqualTo("Marcus Surname");
        assertThat(response.getBody().getAddress()).isEqualTo("Marcus Address");
        assertThat(response.getBody().isAlerting()).isTrue();
        assertThat(response.getBody().getEnergyAlertingThreshold()).isEqualTo(2000.0);

        ResponseEntity<UserDto> created = restTemplate.getForEntity(
                "/api/v1/user/" + response.getBody().getId(), UserDto.class
        );

        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(created.getBody()).isNotNull();
        assertThat(created.getBody().getName()).isEqualTo("Marcus");

    }


}
