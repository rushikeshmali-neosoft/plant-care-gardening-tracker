package com.plantcare.plantcatalog.mapper;

import com.plantcare.plantcatalog.dto.CreatePlantRequest;
import com.plantcare.plantcatalog.dto.PlantDto;
import com.plantcare.plantcatalog.dto.UpdatePlantRequest;
import com.plantcare.plantcatalog.entity.Plant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-07T18:33:18+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class PlantMapperImpl implements PlantMapper {

    @Override
    public PlantDto toDto(Plant plant) {
        if ( plant == null ) {
            return null;
        }

        PlantDto.PlantDtoBuilder plantDto = PlantDto.builder();

        plantDto.commonName( plant.getCommonName() );
        plantDto.createdAt( plant.getCreatedAt() );
        plantDto.id( plant.getId() );
        plantDto.locationType( plant.getLocationType() );
        plantDto.purchaseDate( plant.getPurchaseDate() );
        plantDto.roomGarden( plant.getRoomGarden() );
        plantDto.scientificName( plant.getScientificName() );
        plantDto.source( plant.getSource() );
        plantDto.species( plant.getSpecies() );
        plantDto.status( plant.getStatus() );
        plantDto.updatedAt( plant.getUpdatedAt() );
        plantDto.variety( plant.getVariety() );

        return plantDto.build();
    }

    @Override
    public Plant toEntity(CreatePlantRequest request) {
        if ( request == null ) {
            return null;
        }

        Plant.PlantBuilder plant = Plant.builder();

        plant.commonName( request.getCommonName() );
        plant.locationType( request.getLocationType() );
        plant.purchaseDate( request.getPurchaseDate() );
        plant.roomGarden( request.getRoomGarden() );
        plant.scientificName( request.getScientificName() );
        plant.source( request.getSource() );
        plant.species( request.getSpecies() );
        plant.status( request.getStatus() );
        plant.variety( request.getVariety() );

        return plant.build();
    }

    @Override
    public void updateEntityFromRequest(UpdatePlantRequest request, Plant plant) {
        if ( request == null ) {
            return;
        }

        plant.setCommonName( request.getCommonName() );
        plant.setLocationType( request.getLocationType() );
        plant.setPurchaseDate( request.getPurchaseDate() );
        plant.setRoomGarden( request.getRoomGarden() );
        plant.setScientificName( request.getScientificName() );
        plant.setSource( request.getSource() );
        plant.setSpecies( request.getSpecies() );
        plant.setStatus( request.getStatus() );
        plant.setVariety( request.getVariety() );
    }
}
