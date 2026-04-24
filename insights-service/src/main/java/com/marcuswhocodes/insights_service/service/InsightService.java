package com.marcuswhocodes.insights_service.service;

import com.marcuswhocodes.insights_service.model.dto.InsightDto;

public interface InsightService {
    InsightDto getSavingsTips(Long userId);
    InsightDto getOverview(Long userId);
}
