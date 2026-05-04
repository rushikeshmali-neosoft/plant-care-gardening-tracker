package com.plantcare.activitylogging.service;

import com.plantcare.activitylogging.dto.CareLogDto;
import com.plantcare.activitylogging.dto.CreateCareLogRequest;
import com.plantcare.activitylogging.entity.CareLog;
import com.plantcare.activitylogging.mapper.CareLogMapper;
import com.plantcare.activitylogging.repository.CareLogRepository;
import com.plantcare.plantcatalog.entity.Plant;
import com.plantcare.plantcatalog.exception.PlantNotFoundException;
import com.plantcare.plantcatalog.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CareLogService {

    private final CareLogRepository careLogRepository;
    private final PlantRepository plantRepository;
    private final CareLogMapper careLogMapper;

    public Page<CareLogDto> getLogsForPlant(Long plantId, Long userId, Pageable pageable) {
        // Ensure the plant belongs to the user
        Plant plant = plantRepository.findByIdAndUserId(plantId, userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found or does not belong to user"));

        return careLogRepository.findByPlantIdOrderByLogDateDesc(plantId, pageable)
                .map(careLogMapper::toDto);
    }

    public CareLogDto createLog(Long plantId, Long userId, CreateCareLogRequest request) {
        Plant plant = plantRepository.findByIdAndUserId(plantId, userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found or does not belong to user"));

        CareLog log = careLogMapper.toEntity(request);
        log.setPlant(plant);

        CareLog savedLog = careLogRepository.save(log);
        return careLogMapper.toDto(savedLog);
    }
}



