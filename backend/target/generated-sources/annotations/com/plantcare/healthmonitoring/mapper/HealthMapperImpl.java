package com.plantcare.healthmonitoring.mapper;

import com.plantcare.healthmonitoring.dto.CreateHealthIndicatorRequest;
import com.plantcare.healthmonitoring.dto.HealthIndicatorDto;
import com.plantcare.healthmonitoring.entity.HealthIndicator;
import com.plantcare.plantcatalog.entity.Plant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-05T12:06:56+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Oracle Corporation)"
)
@Component
public class HealthMapperImpl implements HealthMapper {

    @Override
    public HealthIndicatorDto toDto(HealthIndicator indicator) {
        if ( indicator == null ) {
            return null;
        }

        HealthIndicatorDto.HealthIndicatorDtoBuilder healthIndicatorDto = HealthIndicatorDto.builder();

        healthIndicatorDto.plantId( indicatorPlantId( indicator ) );
        healthIndicatorDto.id( indicator.getId() );
        healthIndicatorDto.healthStatus( indicator.getHealthStatus() );
        healthIndicatorDto.recordedDate( indicator.getRecordedDate() );
        healthIndicatorDto.notes( indicator.getNotes() );
        healthIndicatorDto.createdAt( indicator.getCreatedAt() );

        return healthIndicatorDto.build();
    }

    @Override
    public HealthIndicator toEntity(CreateHealthIndicatorRequest request) {
        if ( request == null ) {
            return null;
        }

        HealthIndicator.HealthIndicatorBuilder healthIndicator = HealthIndicator.builder();

        healthIndicator.healthStatus( request.getHealthStatus() );
        healthIndicator.recordedDate( request.getRecordedDate() );
        healthIndicator.notes( request.getNotes() );

        return healthIndicator.build();
    }

    private Long indicatorPlantId(HealthIndicator healthIndicator) {
        if ( healthIndicator == null ) {
            return null;
        }
        Plant plant = healthIndicator.getPlant();
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
