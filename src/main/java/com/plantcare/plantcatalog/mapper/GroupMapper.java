package com.plantcare.plantcatalog.mapper;

import com.plantcare.plantcatalog.dto.CreateGroupRequest;
import com.plantcare.plantcatalog.dto.PlantGroupDto;
import com.plantcare.plantcatalog.entity.Plant;
import com.plantcare.plantcatalog.entity.PlantGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {Plant.class, java.util.stream.Collectors.class})
public interface GroupMapper {

    @Mapping(target = "plantIds", expression = "java(group.getPlants().stream().map(Plant::getId).collect(java.util.stream.Collectors.toList()))")
    PlantGroupDto toDto(PlantGroup group);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "plants", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    PlantGroup toEntity(CreateGroupRequest request);
}



