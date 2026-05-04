package com.plantcare.activitylogging.mapper;

import com.plantcare.activitylogging.dto.CareLogDto;
import com.plantcare.activitylogging.dto.CreateCareLogRequest;
import com.plantcare.activitylogging.entity.CareLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CareLogMapper {

    @Mapping(target = "plantId", source = "plant.id")
    CareLogDto toDto(CareLog careLog);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "plant", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    CareLog toEntity(CreateCareLogRequest request);
}



