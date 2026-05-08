package com.plantcare.healthmonitoring.controller;

import com.plantcare.healthmonitoring.dto.CreateHealthIndicatorRequest;
import com.plantcare.healthmonitoring.dto.HealthAnalysisDto;
import com.plantcare.healthmonitoring.dto.HealthIndicatorDto;
import com.plantcare.healthmonitoring.service.HealthService;
import com.plantcare.sharedinfrastructure.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plants/{plantId}/health")
@RequiredArgsConstructor
@Tag(name = "Health Monitoring", description = "Endpoints for tracking plant health")
@SecurityRequirement(name = "bearerAuth")
public class HealthController {

    private final HealthService healthService;

    @Operation(summary = "Get health indicators", description = "Retrieves health indicators for a specific plant")
    @GetMapping
    public ResponseEntity<List<HealthIndicatorDto>> getHealthIndicators(
            @PathVariable Long plantId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        List<HealthIndicatorDto> indicators = healthService.getHealthIndicators(plantId, userDetails.getUser().getId());
        return ResponseEntity.ok(indicators);
    }

    @Operation(summary = "Get full health analysis", description = "Retrieves a comprehensive health analysis for a specific plant")
    @GetMapping("/analysis")
    public ResponseEntity<HealthAnalysisDto> getFullHealthAnalysis(
            @PathVariable Long plantId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        HealthAnalysisDto analysis = healthService.getFullHealthAnalysis(plantId, userDetails.getUser().getId());
        return ResponseEntity.ok(analysis);
    }

    @Operation(summary = "Add health indicator", description = "Adds a new health indicator for a plant")
    @PostMapping
    public ResponseEntity<HealthIndicatorDto> addHealthIndicator(
            @PathVariable Long plantId,
            @Valid @RequestBody CreateHealthIndicatorRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        HealthIndicatorDto indicator = healthService.addHealthIndicator(plantId, userDetails.getUser().getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(indicator);
    }
}



