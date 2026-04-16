package com.marcuswhocodes.device_service.service.impl;

import com.marcuswhocodes.device_service.domain.dto.DeviceDto;
import com.marcuswhocodes.device_service.domain.entity.Device;
import com.marcuswhocodes.device_service.exceptions.DeviceNotFoundException;
import com.marcuswhocodes.device_service.repository.DeviceRepository;
import com.marcuswhocodes.device_service.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    @Override
    public DeviceDto getDeviceById(Long id) {
        Device device =  deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found with id " + id));
        return mapToDto(device);
    }

    @Override
    public DeviceDto createDevice(DeviceDto deviceDto) {
        Device device = Device.builder()
                .name(deviceDto.getName())
                .type(deviceDto.getType())
                .location(deviceDto.getLocation())
                .userId(deviceDto.getUserId())
                .build();
        final Device savedDevice =deviceRepository.save(device);
        return mapToDto(savedDevice);
    }

    @Override
    public DeviceDto updateDevice(Long id, DeviceDto deviceDto) {
        Device device = deviceRepository.findById(id).orElseThrow(
                () -> new DeviceNotFoundException("Device not found with id " + id)
        );
        device.setName(deviceDto.getName());
        device.setType(deviceDto.getType());
        device.setLocation(deviceDto.getLocation());
        device.setUserId(deviceDto.getUserId());
        final Device updatedDevice = deviceRepository.save(device);
        return mapToDto(updatedDevice);
    }

    @Override
    public void deleteDeviceById(Long id) {
        Device device = deviceRepository.findById(id).orElseThrow(
                () -> new DeviceNotFoundException("Device not found with id " + id)
        );
        deviceRepository.delete(device);
    }

    private DeviceDto mapToDto(Device device) {
        return DeviceDto.builder()
                .name(device.getName())
                .type(device.getType())
                .location(device.getLocation())
                .userId(device.getUserId())
                .build();
    }
}
