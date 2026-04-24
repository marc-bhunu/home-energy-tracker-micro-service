package com.marcuswhocodes.insights_service.model.dto;

import lombok.Builder;

@Builder
public record DeviceDto(
        Long id,
        String name,
        String type,
        String location,
        double energyConsumed
) {
}
