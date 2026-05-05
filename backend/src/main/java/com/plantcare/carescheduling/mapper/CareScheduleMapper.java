package com.plantcare.carescheduling.mapper;

import com.plantcare.carescheduling.dto.CareScheduleDto;
import com.plantcare.carescheduling.dto.CreateCareScheduleRequest;
import com.plantcare.carescheduling.entity.CareSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CareScheduleMapper {

    @Mapping(target = "plantId", source = "plant.id")
    @Mapping(target = "plantName", source = "plant.commonName")
    CareScheduleDto toDto(CareSchedule careSchedule);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "plant", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CareSchedule toEntity(CreateCareScheduleRequest request);
}




