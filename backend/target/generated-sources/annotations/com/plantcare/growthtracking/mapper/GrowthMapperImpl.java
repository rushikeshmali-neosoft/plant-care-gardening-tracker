package com.plantcare.growthtracking.mapper;

import com.plantcare.growthtracking.dto.CreateMeasurementRequest;
import com.plantcare.growthtracking.dto.PlantMeasurementDto;
import com.plantcare.growthtracking.entity.PlantMeasurement;
import com.plantcare.plantcatalog.entity.Plant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-14T03:16:50+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Oracle Corporation)"
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
        plantMeasurementDto.id( measurement.getId() );
        plantMeasurementDto.heightCm( measurement.getHeightCm() );
        plantMeasurementDto.widthCm( measurement.getWidthCm() );
        plantMeasurementDto.measurementDate( measurement.getMeasurementDate() );
        plantMeasurementDto.notes( measurement.getNotes() );
        plantMeasurementDto.createdAt( measurement.getCreatedAt() );

        return plantMeasurementDto.build();
    }

    @Override
    public PlantMeasurement toEntity(CreateMeasurementRequest request) {
        if ( request == null ) {
            return null;
        }

        PlantMeasurement.PlantMeasurementBuilder plantMeasurement = PlantMeasurement.builder();

        plantMeasurement.heightCm( request.getHeightCm() );
        plantMeasurement.widthCm( request.getWidthCm() );
        plantMeasurement.measurementDate( request.getMeasurementDate() );
        plantMeasurement.notes( request.getNotes() );

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
