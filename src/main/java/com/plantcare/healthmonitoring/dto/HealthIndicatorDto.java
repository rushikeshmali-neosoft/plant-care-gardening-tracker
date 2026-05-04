package com.plantcare.healthmonitoring.dto;

import com.plantcare.healthmonitoring.entity.HealthIndicator;
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
public class HealthIndicatorDto {
    private Long id;
    private Long plantId;
    private HealthIndicator.HealthStatus healthStatus;
    private LocalDate recordedDate;
    private String notes;
    private LocalDateTime createdAt;
}



