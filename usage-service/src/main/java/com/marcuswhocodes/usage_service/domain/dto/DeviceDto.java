package com.marcuswhocodes.usage_service.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import lombok.Builder;
import lombok.Setter;

@Builder
public record DeviceDto(Long id,
                        String name,
                        String type,
                        String location,
                        Long userId,
                        Double energyConsumed) {
}
