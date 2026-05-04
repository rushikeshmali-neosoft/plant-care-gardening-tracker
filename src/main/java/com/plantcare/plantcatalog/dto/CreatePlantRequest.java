package com.plantcare.plantcatalog.dto;

import com.plantcare.plantcatalog.entity.Plant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlantRequest {
    
    private String scientificName;
    
    @NotBlank(message = "Common name is required")
    private String commonName;
    
    private String species;
    private String variety;
    private LocalDate purchaseDate;
    private String source;
    
    @NotNull(message = "Location type is required")
    private Plant.LocationType locationType;
    
    private String roomGarden;
    
    @NotNull(message = "Status is required")
    private Plant.PlantStatus status;
}



