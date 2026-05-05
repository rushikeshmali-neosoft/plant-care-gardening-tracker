package com.plantcare.healthmonitoring.service;

import com.plantcare.healthmonitoring.dto.*;
import com.plantcare.healthmonitoring.entity.*;
import com.plantcare.healthmonitoring.mapper.HealthMapper;
import com.plantcare.healthmonitoring.repository.*;
import com.plantcare.plantcatalog.entity.Plant;
import com.plantcare.plantcatalog.exception.PlantNotFoundException;
import com.plantcare.plantcatalog.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HealthService {

    private final HealthIndicatorRepository healthIndicatorRepository;
    private final SymptomLogRepository symptomLogRepository;
    private final TreatmentHistoryRepository treatmentHistoryRepository;
    private final EnvironmentalFactorRepository environmentalFactorRepository;
    private final RecoveryRecordRepository recoveryRecordRepository;
    private final PlantRepository plantRepository;
    private final HealthMapper healthMapper;

    public HealthAnalysisDto getFullHealthAnalysis(Long plantId, Long userId) {
        plantRepository.findByIdAndUserId(plantId, userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found or does not belong to user"));

        return HealthAnalysisDto.builder()
                .indicators(healthIndicatorRepository.findByPlantIdOrderByRecordedDateDesc(plantId).stream()
                        .map(healthMapper::toDto).collect(Collectors.toList()))
                .symptoms(symptomLogRepository.findByPlantIdOrderByObservedDateDesc(plantId).stream()
                        .map(healthMapper::toDto).collect(Collectors.toList()))
                .treatments(treatmentHistoryRepository.findByPlantIdOrderByAppliedDateDesc(plantId).stream()
                        .map(healthMapper::toDto).collect(Collectors.toList()))
                .environmentalFactors(environmentalFactorRepository.findByPlantIdOrderByRecordedAtDesc(plantId).stream()
                        .map(healthMapper::toDto).collect(Collectors.toList()))
                .recoveryRecords(recoveryRecordRepository.findByPlantIdOrderByUpdatedAtDesc(plantId).stream()
                        .map(healthMapper::toDto).collect(Collectors.toList()))
                .build();
    }

    public void adjustHealthScore(Long plantId, int delta) {
        HealthIndicator latest = healthIndicatorRepository.findByPlantIdOrderByRecordedDateDesc(plantId)
                .stream().findFirst().orElse(null);

        int newScore = 85; // Default
        if (latest != null) {
            newScore = Math.max(0, Math.min(100, latest.getHealthScore() + delta));
        } else {
            newScore = Math.max(0, Math.min(100, 85 + delta));
        }

        HealthIndicator.HealthStatus newStatus = determineStatus(newScore);

        HealthIndicator indicator = HealthIndicator.builder()
                .plant(plantRepository.findById(plantId).orElseThrow())
                .healthScore(newScore)
                .healthStatus(newStatus)
                .recordedDate(LocalDate.now())
                .notes("Automated adjustment based on care activity")
                .build();

        healthIndicatorRepository.save(indicator);
    }

    private HealthIndicator.HealthStatus determineStatus(int score) {
        if (score >= 80) return HealthIndicator.HealthStatus.EXCELLENT;
        if (score >= 60) return HealthIndicator.HealthStatus.GOOD;
        if (score >= 30) return HealthIndicator.HealthStatus.POOR;
        return HealthIndicator.HealthStatus.CRITICAL;
    }

    public HealthIndicatorDto addHealthIndicator(Long plantId, Long userId, CreateHealthIndicatorRequest request) {
        Plant plant = plantRepository.findByIdAndUserId(plantId, userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found or does not belong to user"));

        HealthIndicator indicator = healthMapper.toEntity(request);
        indicator.setPlant(plant);
        
        if (indicator.getHealthScore() == null) {
            indicator.setHealthScore(calculateScoreFromStatus(indicator.getHealthStatus()));
        }

        HealthIndicator saved = healthIndicatorRepository.save(indicator);
        return healthMapper.toDto(saved);
    }

    private int calculateScoreFromStatus(HealthIndicator.HealthStatus status) {
        switch (status) {
            case EXCELLENT: return 95;
            case GOOD: return 75;
            case POOR: return 45;
            case CRITICAL: return 15;
            default: return 85;
        }
    }
}



