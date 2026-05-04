package com.plantcare.growthtracking.service;

import com.plantcare.growthtracking.dto.CreateMeasurementRequest;
import com.plantcare.growthtracking.dto.PlantMeasurementDto;
import com.plantcare.growthtracking.entity.PlantMeasurement;
import com.plantcare.growthtracking.mapper.GrowthMapper;
import com.plantcare.growthtracking.repository.PlantMeasurementRepository;
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
public class GrowthService {

    private final PlantMeasurementRepository measurementRepository;
    private final PlantRepository plantRepository;
    private final GrowthMapper growthMapper;

    public List<PlantMeasurementDto> getMeasurements(Long plantId, Long userId) {
        plantRepository.findByIdAndUserId(plantId, userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found or does not belong to user"));

        return measurementRepository.findByPlantIdOrderByMeasurementDateDesc(plantId).stream()
                .map(growthMapper::toDto)
                .collect(Collectors.toList());
    }

    public PlantMeasurementDto addMeasurement(Long plantId, Long userId, CreateMeasurementRequest request) {
        Plant plant = plantRepository.findByIdAndUserId(plantId, userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found or does not belong to user"));

        PlantMeasurement measurement = growthMapper.toEntity(request);
        measurement.setPlant(plant);

        PlantMeasurement saved = measurementRepository.save(measurement);
        return growthMapper.toDto(saved);
    }
}



