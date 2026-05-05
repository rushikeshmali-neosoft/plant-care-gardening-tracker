package com.plantcare.plantcatalog.mapper;

import com.plantcare.plantcatalog.dto.CreateGroupRequest;
import com.plantcare.plantcatalog.dto.PlantGroupDto;
import com.plantcare.plantcatalog.entity.Plant;
import com.plantcare.plantcatalog.entity.PlantGroup;
import java.util.stream.Collectors;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-05T12:28:25+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Oracle Corporation)"
)
@Component
public class GroupMapperImpl implements GroupMapper {

    @Override
    public PlantGroupDto toDto(PlantGroup group) {
        if ( group == null ) {
            return null;
        }

        PlantGroupDto.PlantGroupDtoBuilder plantGroupDto = PlantGroupDto.builder();

        plantGroupDto.id( group.getId() );
        plantGroupDto.name( group.getName() );
        plantGroupDto.description( group.getDescription() );
        plantGroupDto.createdAt( group.getCreatedAt() );

        plantGroupDto.plantIds( group.getPlants().stream().map(Plant::getId).collect(java.util.stream.Collectors.toList()) );

        return plantGroupDto.build();
    }

    @Override
    public PlantGroup toEntity(CreateGroupRequest request) {
        if ( request == null ) {
            return null;
        }

        PlantGroup.PlantGroupBuilder plantGroup = PlantGroup.builder();

        plantGroup.name( request.getName() );
        plantGroup.description( request.getDescription() );

        return plantGroup.build();
    }
}
