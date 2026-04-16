package com.marcuswhocodes.device_service.domain.dto;

import com.marcuswhocodes.device_service.domain.enums.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceDto {
    private String name;
    private DeviceType type;
    private String location;
    private Long userId;
}
