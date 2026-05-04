package com.plantcare.plantcatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantGroupDto {
    private Long id;
    private String name;
    private String description;
    private List<Long> plantIds;
    private LocalDateTime createdAt;
}



