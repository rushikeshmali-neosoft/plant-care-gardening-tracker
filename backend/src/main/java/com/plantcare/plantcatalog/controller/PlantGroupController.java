package com.plantcare.plantcatalog.controller;

import com.plantcare.plantcatalog.dto.CreateGroupRequest;
import com.plantcare.plantcatalog.dto.PlantGroupDto;
import com.plantcare.plantcatalog.service.GroupService;
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
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
@Tag(name = "Plant Groups", description = "Endpoints for managing plant groups")
@SecurityRequirement(name = "bearerAuth")
public class PlantGroupController {

    private final GroupService groupService;

    @Operation(summary = "Get user groups", description = "Retrieves all groups for the authenticated user")
    @GetMapping
    public ResponseEntity<List<PlantGroupDto>> getUserGroups(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(groupService.getUserGroups(userDetails.getUser().getId()));
    }

    @Operation(summary = "Create plant group", description = "Creates a new plant group")
    @PostMapping
    public ResponseEntity<PlantGroupDto> createGroup(
            @Valid @RequestBody CreateGroupRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(groupService.createGroup(userDetails.getUser().getId(), request));
    }

    @Operation(summary = "Add plant to group", description = "Adds a plant to a specific group")
    @PostMapping("/{groupId}/plants/{plantId}")
    public ResponseEntity<Void> addPlantToGroup(
            @PathVariable Long groupId,
            @PathVariable Long plantId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        groupService.addPlantToGroup(groupId, plantId, userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove plant from group", description = "Removes a plant from a specific group")
    @DeleteMapping("/{groupId}/plants/{plantId}")
    public ResponseEntity<Void> removePlantFromGroup(
            @PathVariable Long groupId,
            @PathVariable Long plantId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        groupService.removePlantFromGroup(groupId, plantId, userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete group", description = "Deletes a specific plant group")
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(
            @PathVariable Long groupId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        groupService.deleteGroup(groupId, userDetails.getUser().getId());
        return ResponseEntity.noContent().build();
    }
}



