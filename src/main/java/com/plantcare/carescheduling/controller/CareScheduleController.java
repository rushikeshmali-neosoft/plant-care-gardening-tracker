package com.plantcare.carescheduling.controller;

import com.plantcare.carescheduling.dto.CareScheduleDto;
import com.plantcare.carescheduling.dto.CreateCareScheduleRequest;
import com.plantcare.carescheduling.service.CareScheduleService;
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
@RequestMapping("/api/v1/plants/{plantId}/schedules")
@RequiredArgsConstructor
@Tag(name = "Care Scheduling", description = "Endpoints for managing plant care schedules")
@SecurityRequirement(name = "bearerAuth")
public class CareScheduleController {

    private final CareScheduleService careScheduleService;

    @Operation(summary = "Get schedules for plant", description = "Retrieves all care schedules for a specific plant")
    @GetMapping
    public ResponseEntity<List<CareScheduleDto>> getSchedulesForPlant(
            @PathVariable Long plantId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        List<CareScheduleDto> schedules = careScheduleService.getSchedulesForPlant(plantId, userDetails.getUser().getId());
        return ResponseEntity.ok(schedules);
    }

    @Operation(summary = "Create care schedule", description = "Creates a new care schedule for a plant")
    @PostMapping
    public ResponseEntity<CareScheduleDto> createSchedule(
            @PathVariable Long plantId,
            @Valid @RequestBody CreateCareScheduleRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        CareScheduleDto schedule = careScheduleService.createSchedule(plantId, userDetails.getUser().getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(schedule);
    }

    @Operation(summary = "Mark schedule complete", description = "Marks a care activity as complete and schedules the next due date")
    @PostMapping("/{scheduleId}/complete")
    public ResponseEntity<Void> markScheduleComplete(
            @PathVariable Long plantId,
            @PathVariable Long scheduleId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        careScheduleService.markScheduleComplete(scheduleId, userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }
    
    @Operation(summary = "Delete care schedule", description = "Deletes a specific care schedule")
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long plantId,
            @PathVariable Long scheduleId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        careScheduleService.deleteSchedule(scheduleId, userDetails.getUser().getId());
        return ResponseEntity.noContent().build();
    }
}




