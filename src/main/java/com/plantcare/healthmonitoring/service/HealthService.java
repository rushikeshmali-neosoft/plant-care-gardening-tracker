package com.plantcare.healthmonitoring.service;

import com.plantcare.healthmonitoring.dto.CreateHealthIndicatorRequest;
import com.plantcare.healthmonitoring.dto.HealthIndicatorDto;
import com.plantcare.healthmonitoring.entity.HealthIndicator;
import com.plantcare.healthmonitoring.mapper.HealthMapper;
import com.plantcare.healthmonitoring.repository.HealthIndicatorRepository;
import com.plantcare.plantcatalog.entity.Plant;
import com.plantcare.plantcatalog.exception.PlantNotFoundException;
import com.plantcare.plantcatalog.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HealthService {

    private final HealthIndicatorRepository healthIndicatorRepository;
    private final PlantRepository plantRepository;
    private final HealthMapper healthMapper;

    public List<HealthIndicatorDto> getHealthIndicators(Long plantId, Long userId) {
        plantRepository.findByIdAndUserId(plantId, userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found or does not belong to user"));

        return healthIndicatorRepository.findByPlantIdOrderByRecordedDateDesc(plantId).stream()
                .map(healthMapper::toDto)
                .collect(Collectors.toList());
    }

    public HealthIndicatorDto addHealthIndicator(Long plantId, Long userId, CreateHealthIndicatorRequest request) {
        Plant plant = plantRepository.findByIdAndUserId(plantId, userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found or does not belong to user"));

        HealthIndicator indicator = healthMapper.toEntity(request);
        indicator.setPlant(plant);

        HealthIndicator saved = healthIndicatorRepository.save(indicator);
        return healthMapper.toDto(saved);
    }
}



