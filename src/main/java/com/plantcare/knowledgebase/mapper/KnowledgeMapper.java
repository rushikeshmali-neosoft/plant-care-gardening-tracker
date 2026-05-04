package com.plantcare.knowledgebase.mapper;

import com.plantcare.knowledgebase.dto.CareGuideDto;
import com.plantcare.knowledgebase.entity.CareGuide;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KnowledgeMapper {

    CareGuideDto toDto(CareGuide careGuide);
}



