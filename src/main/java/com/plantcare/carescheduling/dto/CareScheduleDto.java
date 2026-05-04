package com.plantcare.carescheduling.dto;

import com.plantcare.carescheduling.entity.CareSchedule;
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
public class CareScheduleDto {
    private Long id;
    private Long plantId;
    private CareSchedule.CareType careType;
    private Integer frequencyDays;
    private LocalDate nextDueDate;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}




