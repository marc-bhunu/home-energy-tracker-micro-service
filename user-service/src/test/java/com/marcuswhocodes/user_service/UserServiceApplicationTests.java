package com.marcuswhocodes.user_service;

import com.marcuswhocodes.user_service.domain.entity.User;
import com.marcuswhocodes.user_service.repositorty.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@Disabled
class UserServiceApplicationTests {


	@Autowired
	private UserRepository userRepository;

	@Disabled
	@Test
	void createUsers(){
		for (int i = 0; i < 10; i++) {
			var user = User.builder()
					.name("User" + i)
					.surname("Surname" + i)
					.email("user" + i + "@example.com")
					.address("address" + i + " Example Street")
					.alerting(i % 2 == 0)
					.energyAlertingThreshold(100.0 + i )
					.build();
			userRepository.save(user);
		}
		log.info("User Repository populated successfully ", userRepository.findAll());
	}

	@Test
	void contextLoads() {

	}

}
