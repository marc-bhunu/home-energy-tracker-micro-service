package com.marcuswhocodes.device_service;

import com.marcuswhocodes.device_service.domain.entity.Device;
import com.marcuswhocodes.device_service.domain.enums.DeviceType;
import com.marcuswhocodes.device_service.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@Slf4j
@SpringBootTest
class DeviceServiceApplicationTests {

	@Autowired
	private DeviceRepository  deviceRepository;

	Random  random = new Random();

	@Test
	void contextLoads() {
	}

	@Disabled
	@Test
	void createDevice() {
		for (int i = 0; i <= 100 ; i++) {
			var device = Device.builder()
					.name("Device" + i)
					.type(DeviceType.values()[i % DeviceType.values().length])
					.location("Location" + ((i % 3) + 1))
					.userId((long) random.nextLong(1, 10) )
					.build();
			deviceRepository.save(device);
		}
		log.info("Device saved successfully",  deviceRepository.findAll());
	}

}
