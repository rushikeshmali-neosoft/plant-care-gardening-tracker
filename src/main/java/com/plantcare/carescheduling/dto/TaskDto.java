package com.plantcare.carescheduling.dto;

import com.plantcare.carescheduling.entity.CareSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long scheduleId;
    private Long plantId;
    private String plantName;
    private CareSchedule.CareType type;
    private LocalDate dueDate;
    private boolean isOverdue;
}




