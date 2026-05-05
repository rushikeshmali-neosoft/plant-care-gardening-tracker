package com.plantcare.healthmonitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentalFactorDto {
    private Long id;
    private Long plantId;
    private Double temperature;
    private Double humidity;
    private String lightCondition;
    private LocalDateTime recordedAt;
}
