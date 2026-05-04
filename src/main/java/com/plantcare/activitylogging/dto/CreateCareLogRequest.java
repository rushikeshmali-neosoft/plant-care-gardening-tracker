package com.plantcare.activitylogging.dto;

import com.plantcare.carescheduling.entity.CareSchedule;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCareLogRequest {

    @NotNull(message = "Care type is required")
    private CareSchedule.CareType careType;

    @NotNull(message = "Log date is required")
    private LocalDateTime logDate;

    private String notes;
}



