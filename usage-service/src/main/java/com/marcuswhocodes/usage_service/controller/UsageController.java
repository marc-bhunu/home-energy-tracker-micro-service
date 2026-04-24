package com.marcuswhocodes.usage_service.controller;

import com.marcuswhocodes.usage_service.domain.dto.UsageDto;
import com.marcuswhocodes.usage_service.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usage")
@RequiredArgsConstructor
public class UsageController {

    private final UsageService  usageService;

    @GetMapping("/{userId}")
    public ResponseEntity<UsageDto> getInsights(@PathVariable Long userId, @RequestParam(defaultValue = "3") int days) {
        final UsageDto usage = usageService.getXDaysUsageForUser(userId, days);
        return ResponseEntity.ok(usage);
    }
}
