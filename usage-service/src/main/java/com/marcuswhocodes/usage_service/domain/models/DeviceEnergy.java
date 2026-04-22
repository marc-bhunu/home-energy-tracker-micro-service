package com.marcuswhocodes.usage_service.domain.models;

import lombok.Builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceEnergy {
    private Long deviceId;
    private double energyConsumed;
    private Long userId;
}
