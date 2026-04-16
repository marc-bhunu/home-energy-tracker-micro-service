package com.marcuswhocodes.device_service.repository;

import com.marcuswhocodes.device_service.domain.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
}
