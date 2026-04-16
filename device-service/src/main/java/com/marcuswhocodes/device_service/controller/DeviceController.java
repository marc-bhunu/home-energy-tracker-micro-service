package com.marcuswhocodes.device_service.controller;

import com.marcuswhocodes.device_service.domain.dto.DeviceDto;
import com.marcuswhocodes.device_service.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/devices")
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> getDeviceById(@PathVariable Long id){
        DeviceDto device = deviceService.getDeviceById(id);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<DeviceDto> createDevice(@RequestBody DeviceDto deviceDto){
        DeviceDto createdDevice  = deviceService.createDevice(deviceDto);
        return new ResponseEntity<>(createdDevice, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceDto> updateDeviceById(@PathVariable Long id,
                                                      @RequestBody DeviceDto deviceDto){
        DeviceDto updatedDevice = deviceService.updateDevice(id, deviceDto);
        return  new ResponseEntity<>(updatedDevice, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceById(@PathVariable Long id){
        try{
            deviceService.deleteDeviceById(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
