package com.plantcare.carescheduling.mapper;

import com.plantcare.carescheduling.dto.CareScheduleDto;
import com.plantcare.carescheduling.dto.CreateCareScheduleRequest;
import com.plantcare.carescheduling.entity.CareSchedule;
import com.plantcare.plantcatalog.entity.Plant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-12T13:34:57+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Oracle Corporation)"
)
@Component
public class CareScheduleMapperImpl implements CareScheduleMapper {

    @Override
    public CareScheduleDto toDto(CareSchedule careSchedule) {
        if ( careSchedule == null ) {
            return null;
        }

        CareScheduleDto.CareScheduleDtoBuilder careScheduleDto = CareScheduleDto.builder();

        careScheduleDto.plantId( careSchedulePlantId( careSchedule ) );
        careScheduleDto.plantName( careSchedulePlantCommonName( careSchedule ) );
        careScheduleDto.id( careSchedule.getId() );
        careScheduleDto.careType( careSchedule.getCareType() );
        careScheduleDto.frequencyDays( careSchedule.getFrequencyDays() );
        careScheduleDto.nextDueDate( careSchedule.getNextDueDate() );
        careScheduleDto.notes( careSchedule.getNotes() );
        careScheduleDto.createdAt( careSchedule.getCreatedAt() );
        careScheduleDto.updatedAt( careSchedule.getUpdatedAt() );

        return careScheduleDto.build();
    }

    @Override
    public CareSchedule toEntity(CreateCareScheduleRequest request) {
        if ( request == null ) {
            return null;
        }

        CareSchedule.CareScheduleBuilder careSchedule = CareSchedule.builder();

        careSchedule.careType( request.getCareType() );
        careSchedule.frequencyDays( request.getFrequencyDays() );
        careSchedule.nextDueDate( request.getNextDueDate() );
        careSchedule.notes( request.getNotes() );

        return careSchedule.build();
    }

    private Long careSchedulePlantId(CareSchedule careSchedule) {
        if ( careSchedule == null ) {
            return null;
        }
        Plant plant = careSchedule.getPlant();
        if ( plant == null ) {
            return null;
        }
        Long id = plant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String careSchedulePlantCommonName(CareSchedule careSchedule) {
        if ( careSchedule == null ) {
            return null;
        }
        Plant plant = careSchedule.getPlant();
        if ( plant == null ) {
            return null;
        }
        String commonName = plant.getCommonName();
        if ( commonName == null ) {
            return null;
        }
        return commonName;
    }
}
