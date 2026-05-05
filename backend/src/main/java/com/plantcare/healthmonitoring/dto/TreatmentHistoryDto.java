package com.plantcare.healthmonitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentHistoryDto {
    private Long id;
    private Long plantId;
    private String treatmentName;
    private LocalDate appliedDate;
    private String notes;
    private Boolean isEffective;
}
