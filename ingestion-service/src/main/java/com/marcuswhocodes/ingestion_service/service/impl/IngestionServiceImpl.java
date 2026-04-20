package com.marcuswhocodes.ingestion_service.service.impl;

import com.marcuswhocodes.ingestion_service.dto.EnergyUsageDto;
import com.marcuswhocodes.ingestion_service.service.IngestionService;
import com.marcuswhocodes.kafak.event.EnergyUsageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IngestionServiceImpl implements IngestionService {

    private final KafkaTemplate<String, EnergyUsageEvent> kafkaTemplate;

    public IngestionServiceImpl(KafkaTemplate<String, EnergyUsageEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public void ingestEnergyUsage(EnergyUsageDto energyUsageDto) {
        EnergyUsageEvent event = EnergyUsageEvent.builder()
                .deviceId(energyUsageDto.deviceId())
                .energyConsumed(energyUsageDto.energyConsumed())
                .timestamp(energyUsageDto.timestamp())
                .build();

        kafkaTemplate.send("energy-usage", event);
        log.info("energy-usage sent to kafka: {}", event);
    }
}
