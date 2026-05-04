package com.plantcare.activitylogging.controller;

import com.plantcare.activitylogging.dto.CareLogDto;
import com.plantcare.activitylogging.dto.CreateCareLogRequest;
import com.plantcare.activitylogging.service.CareLogService;
import com.plantcare.sharedinfrastructure.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/plants/{plantId}/logs")
@RequiredArgsConstructor
@Tag(name = "Care Logging", description = "Endpoints for viewing and managing plant care logs")
@SecurityRequirement(name = "bearerAuth")
public class CareLogController {

    private final CareLogService careLogService;

    @Operation(summary = "Get care logs for a plant", description = "Retrieves a paginated list of care logs for a specific plant")
    @GetMapping
    public ResponseEntity<Page<CareLogDto>> getLogsForPlant(
            @PathVariable Long plantId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Pageable pageable) {
        
        Page<CareLogDto> logs = careLogService.getLogsForPlant(plantId, userDetails.getUser().getId(), pageable);
        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Create care log", description = "Creates a new care log entry for a plant manually")
    @PostMapping
    public ResponseEntity<CareLogDto> createLog(
            @PathVariable Long plantId,
            @Valid @RequestBody CreateCareLogRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        CareLogDto log = careLogService.createLog(plantId, userDetails.getUser().getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(log);
    }
}



