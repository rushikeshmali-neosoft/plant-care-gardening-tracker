package com.plantcare.knowledgebase.service;

import com.plantcare.knowledgebase.dto.CareGuideDto;
import com.plantcare.knowledgebase.mapper.KnowledgeMapper;
import com.plantcare.knowledgebase.repository.CareGuideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KnowledgeService {

    private final CareGuideRepository careGuideRepository;
    private final KnowledgeMapper knowledgeMapper;

    @org.springframework.cache.annotation.Cacheable(value = "care-guides")
    public List<CareGuideDto> getAllGuides() {
        return careGuideRepository.findAll().stream()
                .map(knowledgeMapper::toDto)
                .collect(Collectors.toList());
    }

    @org.springframework.cache.annotation.Cacheable(value = "care-guides", key = "#plantType")
    public List<CareGuideDto> getGuidesByPlantType(String plantType) {
        return careGuideRepository.findByPlantTypeContainingIgnoreCase(plantType).stream()
                .map(knowledgeMapper::toDto)
                .collect(Collectors.toList());
    }

    public CareGuideDto getGuideById(Long id) {
        return careGuideRepository.findById(id)
                .map(knowledgeMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Care Guide not found"));
    }
}



