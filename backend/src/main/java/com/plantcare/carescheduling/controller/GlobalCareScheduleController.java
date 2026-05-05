package com.plantcare.carescheduling.controller;

import com.plantcare.carescheduling.dto.CareScheduleDto;
import com.plantcare.carescheduling.service.CareScheduleService;
import com.plantcare.sharedinfrastructure.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
@Tag(name = "Global Care Scheduling", description = "Endpoints for managing global care schedules")
@SecurityRequirement(name = "bearerAuth")
public class GlobalCareScheduleController {

    private final CareScheduleService careScheduleService;

    @Operation(summary = "Get all schedules", description = "Retrieves all care schedules for the authenticated user")
    @GetMapping
    public ResponseEntity<List<CareScheduleDto>> getAllSchedules(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        List<CareScheduleDto> schedules = careScheduleService.getAllSchedulesForUser(userDetails.getUser().getId());
        return ResponseEntity.ok(schedules);
    }
}
