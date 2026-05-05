package com.plantcare.activitylogging.mapper;

import com.plantcare.activitylogging.dto.CareLogDto;
import com.plantcare.activitylogging.dto.CreateCareLogRequest;
import com.plantcare.activitylogging.entity.CareLog;
import com.plantcare.plantcatalog.entity.Plant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-05T13:04:53+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Oracle Corporation)"
)
@Component
public class CareLogMapperImpl implements CareLogMapper {

    @Override
    public CareLogDto toDto(CareLog careLog) {
        if ( careLog == null ) {
            return null;
        }

        CareLogDto.CareLogDtoBuilder careLogDto = CareLogDto.builder();

        careLogDto.plantId( careLogPlantId( careLog ) );
        careLogDto.id( careLog.getId() );
        careLogDto.careType( careLog.getCareType() );
        careLogDto.logDate( careLog.getLogDate() );
        careLogDto.notes( careLog.getNotes() );
        careLogDto.createdAt( careLog.getCreatedAt() );

        return careLogDto.build();
    }

    @Override
    public CareLog toEntity(CreateCareLogRequest request) {
        if ( request == null ) {
            return null;
        }

        CareLog.CareLogBuilder careLog = CareLog.builder();

        careLog.careType( request.getCareType() );
        careLog.logDate( request.getLogDate() );
        careLog.notes( request.getNotes() );

        return careLog.build();
    }

    private Long careLogPlantId(CareLog careLog) {
        if ( careLog == null ) {
            return null;
        }
        Plant plant = careLog.getPlant();
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
