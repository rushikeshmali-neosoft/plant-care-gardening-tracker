package com.plantcare.carescheduling.dto;

import com.plantcare.carescheduling.entity.CareSchedule;
import jakarta.validation.constraints.Min;
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
public class CreateCareScheduleRequest {

    @NotNull(message = "Care type is required")
    private CareSchedule.CareType careType;

    @NotNull(message = "Frequency is required")
    @Min(value = 1, message = "Frequency must be at least 1 day")
    private Integer frequencyDays;

    private LocalDate nextDueDate;

    private String notes;
}




