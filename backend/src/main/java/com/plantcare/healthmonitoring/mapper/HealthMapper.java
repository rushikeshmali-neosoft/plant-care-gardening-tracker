package com.plantcare.healthmonitoring.mapper;

import com.plantcare.healthmonitoring.dto.*;
import com.plantcare.healthmonitoring.entity.*;
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

    @Mapping(target = "plantId", source = "plant.id")
    SymptomLogDto toDto(SymptomLog log);

    @Mapping(target = "plantId", source = "plant.id")
    TreatmentHistoryDto toDto(TreatmentHistory history);

    @Mapping(target = "plantId", source = "plant.id")
    EnvironmentalFactorDto toDto(EnvironmentalFactor factor);

    @Mapping(target = "plantId", source = "plant.id")
    RecoveryRecordDto toDto(RecoveryRecord record);
}



