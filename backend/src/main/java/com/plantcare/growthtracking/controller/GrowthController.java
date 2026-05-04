package com.plantcare.growthtracking.controller;

import com.plantcare.growthtracking.dto.CreateMeasurementRequest;
import com.plantcare.growthtracking.dto.PlantMeasurementDto;
import com.plantcare.growthtracking.service.GrowthService;
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
@RequestMapping("/api/v1/plants/{plantId}/growth")
@RequiredArgsConstructor
@Tag(name = "Growth Tracking", description = "Endpoints for tracking plant growth and measurements")
@SecurityRequirement(name = "bearerAuth")
public class GrowthController {

    private final GrowthService growthService;

    @Operation(summary = "Get measurements", description = "Retrieves growth measurements for a specific plant")
    @GetMapping
    public ResponseEntity<List<PlantMeasurementDto>> getMeasurements(
            @PathVariable Long plantId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        List<PlantMeasurementDto> measurements = growthService.getMeasurements(plantId, userDetails.getUser().getId());
        return ResponseEntity.ok(measurements);
    }

    @Operation(summary = "Add measurement", description = "Adds a new growth measurement for a plant")
    @PostMapping
    public ResponseEntity<PlantMeasurementDto> addMeasurement(
            @PathVariable Long plantId,
            @Valid @RequestBody CreateMeasurementRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        PlantMeasurementDto measurement = growthService.addMeasurement(plantId, userDetails.getUser().getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(measurement);
    }
}



