package com.plantcare.healthmonitoring.dto;

import com.plantcare.healthmonitoring.entity.SymptomLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SymptomLogDto {
    private Long id;
    private Long plantId;
    private String symptom;
    private SymptomLog.Severity severity;
    private LocalDate observedDate;
    private String notes;
}
