package com.plantcare.plantcatalog.dto;

import com.plantcare.plantcatalog.entity.Plant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantDto {
    private Long id;
    private String scientificName;
    private String commonName;
    private String species;
    private String variety;
    private LocalDate purchaseDate;
    private String source;
    private Plant.LocationType locationType;
    private String roomGarden;
    private Plant.PlantStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}



