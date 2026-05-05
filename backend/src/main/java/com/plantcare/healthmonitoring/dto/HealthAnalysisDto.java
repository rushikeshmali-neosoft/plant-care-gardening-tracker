package com.plantcare.healthmonitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthAnalysisDto {
    private List<HealthIndicatorDto> indicators;
    private List<SymptomLogDto> symptoms;
    private List<TreatmentHistoryDto> treatments;
    private List<EnvironmentalFactorDto> environmentalFactors;
    private List<RecoveryRecordDto> recoveryRecords;
}
