package com.plantcare.healthmonitoring.mapper;

import com.plantcare.healthmonitoring.dto.CreateHealthIndicatorRequest;
import com.plantcare.healthmonitoring.dto.HealthIndicatorDto;
import com.plantcare.healthmonitoring.entity.HealthIndicator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HealthMapper {

    @Mapping(target = "plantId", source = "plant.id")
    HealthIndicatorDto toDto(HealthIndicator indicator);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "plant", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    HealthIndicator toEntity(CreateHealthIndicatorRequest request);
}



