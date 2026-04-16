package com.marcuswhocodes.device_service.service;

import com.marcuswhocodes.device_service.domain.dto.DeviceDto;

public interface DeviceService {
    DeviceDto getDeviceById(Long id);
    DeviceDto createDevice(DeviceDto deviceDto);
    DeviceDto updateDevice(Long id, DeviceDto deviceDto);
    void deleteDeviceById(Long id);
}
