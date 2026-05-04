package com.plantcare.plantcatalog.controller;

import com.plantcare.plantcatalog.dto.CreatePlantRequest;
import com.plantcare.plantcatalog.dto.PlantDto;
import com.plantcare.plantcatalog.dto.UpdatePlantRequest;
import com.plantcare.plantcatalog.service.PlantService;
import com.plantcare.sharedinfrastructure.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/v1/plants")
@RequiredArgsConstructor
@Tag(name = "Plant Management", description = "Endpoints for managing user plants")
@SecurityRequirement(name = "bearerAuth")
public class PlantController {

    private final PlantService plantService;

    @Operation(summary = "Get all plants for user", description = "Retrieves a paginated list of plants for the authenticated user")
    @GetMapping
    public ResponseEntity<Page<PlantDto>> getAllPlants(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Pageable pageable) {
        
        Page<PlantDto> plants = plantService.getAllPlantsForUser(userDetails.getUser().getId(), pageable);
        return ResponseEntity.ok(plants);
    }

    @Operation(summary = "Get plant by ID", description = "Retrieves a specific plant by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Plant retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Plant not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlantDto> getPlantById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        PlantDto plant = plantService.getPlantByIdAndUserId(id, userDetails.getUser().getId());
        return ResponseEntity.ok(plant);
    }

    @Operation(summary = "Add new plant", description = "Creates a new plant for the authenticated user")
    @PostMapping
    public ResponseEntity<PlantDto> createPlant(
            @Valid @RequestBody CreatePlantRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        PlantDto createdPlant = plantService.createPlant(userDetails.getUser().getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlant);
    }

    @Operation(summary = "Update plant details", description = "Updates an existing plant for the authenticated user")
    @PutMapping("/{id}")
    public ResponseEntity<PlantDto> updatePlant(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePlantRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        PlantDto updatedPlant = plantService.updatePlant(id, userDetails.getUser().getId(), request);
        return ResponseEntity.ok(updatedPlant);
    }

    @Operation(summary = "Delete plant", description = "Deletes a specific plant by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlant(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        plantService.deletePlant(id, userDetails.getUser().getId());
        return ResponseEntity.noContent().build();
    }
}



