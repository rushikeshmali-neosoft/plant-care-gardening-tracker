package com.plantcare.knowledgebase.mapper;

import com.plantcare.knowledgebase.dto.CareGuideDto;
import com.plantcare.knowledgebase.entity.CareGuide;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-07T18:33:18+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class KnowledgeMapperImpl implements KnowledgeMapper {

    @Override
    public CareGuideDto toDto(CareGuide careGuide) {
        if ( careGuide == null ) {
            return null;
        }

        CareGuideDto.CareGuideDtoBuilder careGuideDto = CareGuideDto.builder();

        careGuideDto.category( careGuide.getCategory() );
        careGuideDto.content( careGuide.getContent() );
        careGuideDto.createdAt( careGuide.getCreatedAt() );
        careGuideDto.id( careGuide.getId() );
        careGuideDto.plantType( careGuide.getPlantType() );
        careGuideDto.title( careGuide.getTitle() );

        return careGuideDto.build();
    }
}
