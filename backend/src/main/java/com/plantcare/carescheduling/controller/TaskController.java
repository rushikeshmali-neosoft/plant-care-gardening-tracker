package com.plantcare.carescheduling.controller;

import com.plantcare.carescheduling.dto.TaskDto;
import com.plantcare.carescheduling.service.TaskService;
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
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Management", description = "Endpoints for managing care tasks")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Get upcoming tasks", description = "Retrieves tasks due in the next 7 days")
    @GetMapping("/upcoming")
    public ResponseEntity<List<TaskDto>> getUpcomingTasks(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(taskService.getUpcomingTasks(userDetails.getUser().getId()));
    }

    @Operation(summary = "Get overdue tasks", description = "Retrieves tasks with a past due date")
    @GetMapping("/overdue")
    public ResponseEntity<List<TaskDto>> getOverdueTasks(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(taskService.getOverdueTasks(userDetails.getUser().getId()));
    }
}




