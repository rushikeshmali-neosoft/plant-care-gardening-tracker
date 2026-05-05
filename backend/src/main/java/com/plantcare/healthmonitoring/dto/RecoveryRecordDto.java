package com.plantcare.healthmonitoring.dto;

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
public class RecoveryRecordDto {
    private Long id;
    private Long plantId;
    private LocalDate startDate;
    private LocalDate recoveryDate;
    private Integer progressPercentage;
    private String notes;
    private LocalDateTime updatedAt;
}
