package com.plantcare.plantcatalog.mapper;

import com.plantcare.plantcatalog.dto.CreatePlantRequest;
import com.plantcare.plantcatalog.dto.PlantDto;
import com.plantcare.plantcatalog.dto.UpdatePlantRequest;
import com.plantcare.plantcatalog.entity.Plant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-05T12:06:56+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Oracle Corporation)"
)
@Component
public class PlantMapperImpl implements PlantMapper {

    @Override
    public PlantDto toDto(Plant plant) {
        if ( plant == null ) {
            return null;
        }

        PlantDto.PlantDtoBuilder plantDto = PlantDto.builder();

        plantDto.id( plant.getId() );
        plantDto.scientificName( plant.getScientificName() );
        plantDto.commonName( plant.getCommonName() );
        plantDto.species( plant.getSpecies() );
        plantDto.variety( plant.getVariety() );
        plantDto.purchaseDate( plant.getPurchaseDate() );
        plantDto.source( plant.getSource() );
        plantDto.locationType( plant.getLocationType() );
        plantDto.roomGarden( plant.getRoomGarden() );
        plantDto.status( plant.getStatus() );
        plantDto.createdAt( plant.getCreatedAt() );
        plantDto.updatedAt( plant.getUpdatedAt() );

        return plantDto.build();
    }

    @Override
    public Plant toEntity(CreatePlantRequest request) {
        if ( request == null ) {
            return null;
        }

        Plant.PlantBuilder plant = Plant.builder();

        plant.scientificName( request.getScientificName() );
        plant.commonName( request.getCommonName() );
        plant.species( request.getSpecies() );
        plant.variety( request.getVariety() );
        plant.purchaseDate( request.getPurchaseDate() );
        plant.source( request.getSource() );
        plant.locationType( request.getLocationType() );
        plant.roomGarden( request.getRoomGarden() );
        plant.status( request.getStatus() );

        return plant.build();
    }

    @Override
    public void updateEntityFromRequest(UpdatePlantRequest request, Plant plant) {
        if ( request == null ) {
            return;
        }

        plant.setScientificName( request.getScientificName() );
        plant.setCommonName( request.getCommonName() );
        plant.setSpecies( request.getSpecies() );
        plant.setVariety( request.getVariety() );
        plant.setPurchaseDate( request.getPurchaseDate() );
        plant.setSource( request.getSource() );
        plant.setLocationType( request.getLocationType() );
        plant.setRoomGarden( request.getRoomGarden() );
        plant.setStatus( request.getStatus() );
    }
}
