package com.plantcare.activitylogging.dto;

import com.plantcare.carescheduling.entity.CareSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CareLogDto {
    private Long id;
    private Long plantId;
    private CareSchedule.CareType careType;
    private LocalDateTime logDate;
    private String notes;
    private LocalDateTime createdAt;
}



