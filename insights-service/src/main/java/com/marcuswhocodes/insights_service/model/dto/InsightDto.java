package com.marcuswhocodes.insights_service.model.dto;

import lombok.Builder;

@Builder
public record InsightDto(
        Long userId,
        String tips,
        double energyUsage
) {
}
