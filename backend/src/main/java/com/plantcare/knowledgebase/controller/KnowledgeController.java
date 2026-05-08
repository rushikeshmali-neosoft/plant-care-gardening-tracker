package com.plantcare.knowledgebase.controller;

import com.plantcare.knowledgebase.dto.CareGuideDto;
import com.plantcare.knowledgebase.service.KnowledgeService;
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
@RequestMapping("/api/v1/knowledge")
@RequiredArgsConstructor
@Tag(name = "Knowledge Base", description = "Endpoints for accessing care guides and plant knowledge")
@SecurityRequirement(name = "bearerAuth")
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    @Operation(summary = "Get all care guides", description = "Retrieves all available care guides with optional filtering and search")
    @GetMapping("/guides")
    public ResponseEntity<List<CareGuideDto>> getAllGuides(
            @RequestParam(required = false) String plantType,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        List<CareGuideDto> guides;
        if (search != null && !search.isBlank()) {
            guides = knowledgeService.searchGuides(search);
        } else if (category != null && !category.isBlank()) {
            guides = knowledgeService.getGuidesByCategory(category);
        } else if (plantType != null && !plantType.isBlank()) {
            guides = knowledgeService.getGuidesByPlantType(plantType);
        } else {
            guides = knowledgeService.getAllGuides();
        }
        
        return ResponseEntity.ok(guides);
    }

    @Operation(summary = "Get care guide by ID", description = "Retrieves a specific care guide by its ID")
    @GetMapping("/guides/{id}")
    public ResponseEntity<CareGuideDto> getGuideById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        CareGuideDto guide = knowledgeService.getGuideById(id);
        return ResponseEntity.ok(guide);
    }
}



