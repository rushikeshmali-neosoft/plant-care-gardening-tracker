package com.plantcare.growthtracking.dto;

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
public class CreateMeasurementRequest {

    private Double heightCm;
    
    private Double widthCm;

    @NotNull(message = "Measurement date is required")
    private LocalDate measurementDate;

    private String notes;
}



