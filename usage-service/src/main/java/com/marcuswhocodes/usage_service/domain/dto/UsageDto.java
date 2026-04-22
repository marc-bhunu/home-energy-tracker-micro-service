package com.marcuswhocodes.usage_service.domain.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UsageDto(
        Long userId,
        List<DeviceDto> devices
) {
}