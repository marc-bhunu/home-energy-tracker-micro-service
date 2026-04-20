package com.marcuswhocodes.ingestion_service.service;

import com.marcuswhocodes.ingestion_service.dto.EnergyUsageDto;

public interface IngestionService {
    void ingestEnergyUsage(EnergyUsageDto energyUsageDto);
}
