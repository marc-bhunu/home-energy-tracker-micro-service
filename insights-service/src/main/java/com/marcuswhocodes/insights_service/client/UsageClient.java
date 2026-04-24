package com.marcuswhocodes.insights_service.client;

import com.marcuswhocodes.insights_service.model.dto.UsageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class UsageClient {
    private final RestTemplate restTemplate;
    private final String usageUrl;

    public UsageClient(@Value("${usage.service.url}") String usageUrl){
        this.restTemplate = new RestTemplate();
        this.usageUrl = usageUrl;
    }

    public UsageDto getXDaysForUser(Long userId, int days) {
        String url = UriComponentsBuilder
                .fromUriString(usageUrl)
                .path("/{userId}")
                .queryParam("days", days)
                .buildAndExpand(userId)
                .toUriString();
        ResponseEntity<UsageDto> response = restTemplate.getForEntity(url, UsageDto.class);
        return response.getBody();
    }
}
