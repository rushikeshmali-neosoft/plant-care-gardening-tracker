package com.plantcare.notificationengine.controller;

import com.plantcare.notificationengine.dto.NotificationDto;
import com.plantcare.notificationengine.service.NotificationService;
import com.plantcare.sharedinfrastructure.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Engine", description = "Endpoints for managing user notifications")
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "Get user notifications", description = "Retrieves all notifications for the authenticated user")
    @GetMapping
    public ResponseEntity<List<NotificationDto>> getUserNotifications(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        List<NotificationDto> notifications = notificationService.getUserNotifications(userDetails.getUser().getId());
        return ResponseEntity.ok(notifications);
    }

    @Operation(summary = "Mark notification as read", description = "Marks a specific notification as read")
    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        notificationService.markAsRead(id, userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }
}



