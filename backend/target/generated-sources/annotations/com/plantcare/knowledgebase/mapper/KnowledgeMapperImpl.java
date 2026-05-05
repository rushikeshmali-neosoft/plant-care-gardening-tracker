package com.plantcare.knowledgebase.mapper;

import com.plantcare.knowledgebase.dto.CareGuideDto;
import com.plantcare.knowledgebase.entity.CareGuide;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-05T13:04:53+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Oracle Corporation)"
)
@Component
public class KnowledgeMapperImpl implements KnowledgeMapper {

    @Override
    public CareGuideDto toDto(CareGuide careGuide) {
        if ( careGuide == null ) {
            return null;
        }

        CareGuideDto.CareGuideDtoBuilder careGuideDto = CareGuideDto.builder();

        careGuideDto.id( careGuide.getId() );
        careGuideDto.plantType( careGuide.getPlantType() );
        careGuideDto.title( careGuide.getTitle() );
        careGuideDto.content( careGuide.getContent() );
        careGuideDto.createdAt( careGuide.getCreatedAt() );

        return careGuideDto.build();
    }
}
