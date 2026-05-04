package com.plantcare.healthmonitoring.dto;

import com.plantcare.healthmonitoring.entity.HealthIndicator;
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
public class CreateHealthIndicatorRequest {

    @NotNull(message = "Health status is required")
    private HealthIndicator.HealthStatus healthStatus;

    @NotNull(message = "Recorded date is required")
    private LocalDate recordedDate;

    private String notes;
}



