package com.plantcare.growthtracking.mapper;

import com.plantcare.growthtracking.dto.CreateMeasurementRequest;
import com.plantcare.growthtracking.dto.PlantMeasurementDto;
import com.plantcare.growthtracking.entity.PlantMeasurement;
import com.plantcare.plantcatalog.entity.Plant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-07T18:33:17+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class GrowthMapperImpl implements GrowthMapper {

    @Override
    public PlantMeasurementDto toDto(PlantMeasurement measurement) {
        if ( measurement == null ) {
            return null;
        }

        PlantMeasurementDto.PlantMeasurementDtoBuilder plantMeasurementDto = PlantMeasurementDto.builder();

        plantMeasurementDto.plantId( measurementPlantId( measurement ) );
        plantMeasurementDto.createdAt( measurement.getCreatedAt() );
        plantMeasurementDto.heightCm( measurement.getHeightCm() );
        plantMeasurementDto.id( measurement.getId() );
        plantMeasurementDto.measurementDate( measurement.getMeasurementDate() );
        plantMeasurementDto.notes( measurement.getNotes() );
        plantMeasurementDto.widthCm( measurement.getWidthCm() );

        return plantMeasurementDto.build();
    }

    @Override
    public PlantMeasurement toEntity(CreateMeasurementRequest request) {
        if ( request == null ) {
            return null;
        }

        PlantMeasurement.PlantMeasurementBuilder plantMeasurement = PlantMeasurement.builder();

        plantMeasurement.heightCm( request.getHeightCm() );
        plantMeasurement.measurementDate( request.getMeasurementDate() );
        plantMeasurement.notes( request.getNotes() );
        plantMeasurement.widthCm( request.getWidthCm() );

        return plantMeasurement.build();
    }

    private Long measurementPlantId(PlantMeasurement plantMeasurement) {
        if ( plantMeasurement == null ) {
            return null;
        }
        Plant plant = plantMeasurement.getPlant();
        if ( plant == null ) {
            return null;
        }
        Long id = plant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
