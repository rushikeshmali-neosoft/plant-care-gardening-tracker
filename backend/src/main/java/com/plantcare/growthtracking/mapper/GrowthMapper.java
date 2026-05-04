package com.plantcare.growthtracking.mapper;

import com.plantcare.growthtracking.dto.CreateMeasurementRequest;
import com.plantcare.growthtracking.dto.PlantMeasurementDto;
import com.plantcare.growthtracking.entity.PlantMeasurement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GrowthMapper {

    @Mapping(target = "plantId", source = "plant.id")
    PlantMeasurementDto toDto(PlantMeasurement measurement);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "plant", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    PlantMeasurement toEntity(CreateMeasurementRequest request);
}



