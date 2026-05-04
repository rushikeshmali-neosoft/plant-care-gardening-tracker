package com.plantcare.growthtracking.dto;

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
public class PlantMeasurementDto {
    private Long id;
    private Long plantId;
    private Double heightCm;
    private Double widthCm;
    private LocalDate measurementDate;
    private String notes;
    private LocalDateTime createdAt;
}



