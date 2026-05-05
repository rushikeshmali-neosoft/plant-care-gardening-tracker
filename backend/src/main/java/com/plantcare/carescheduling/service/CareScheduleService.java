package com.plantcare.carescheduling.service;

import com.plantcare.carescheduling.dto.CareScheduleDto;
import com.plantcare.carescheduling.dto.CreateCareScheduleRequest;
import com.plantcare.carescheduling.entity.CareSchedule;
import com.plantcare.carescheduling.exception.CareScheduleNotFoundException;
import com.plantcare.carescheduling.mapper.CareScheduleMapper;
import com.plantcare.carescheduling.repository.CareScheduleRepository;
import com.plantcare.plantcatalog.entity.Plant;
import com.plantcare.plantcatalog.exception.PlantNotFoundException;
import com.plantcare.plantcatalog.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CareScheduleService {

    private final CareScheduleRepository careScheduleRepository;
    private final PlantRepository plantRepository;
    private final CareScheduleMapper careScheduleMapper;
    private final com.plantcare.healthmonitoring.service.HealthService healthService;

    public List<CareScheduleDto> getSchedulesForPlant(Long plantId, Long userId) {
        // Ensure the plant belongs to the user
        Plant plant = plantRepository.findByIdAndUserId(plantId, userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found or does not belong to user"));

        return careScheduleRepository.findByPlantId(plantId).stream()
                .map(careScheduleMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CareScheduleDto> getAllSchedulesForUser(Long userId) {
        return careScheduleRepository.findByUserId(userId).stream()
                .map(careScheduleMapper::toDto)
                .collect(Collectors.toList());
    }

    public CareScheduleDto createSchedule(Long plantId, Long userId, CreateCareScheduleRequest request) {
        Plant plant = plantRepository.findByIdAndUserId(plantId, userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found or does not belong to user"));

        CareSchedule schedule = careScheduleMapper.toEntity(request);
        schedule.setPlant(plant);
        
        if (schedule.getNextDueDate() == null) {
            schedule.setNextDueDate(LocalDate.now().plusDays(calculateAdjustedFrequency(schedule)));
        }

        CareSchedule savedSchedule = careScheduleRepository.save(schedule);
        return careScheduleMapper.toDto(savedSchedule);
    }

    public void markScheduleComplete(Long scheduleId, Long userId) {
        CareSchedule schedule = careScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CareScheduleNotFoundException("Care schedule not found"));

        // Verify ownership
        plantRepository.findByIdAndUserId(schedule.getPlant().getId(), userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found or does not belong to user"));

        // Update the next due date based on frequency with seasonal adjustments
        schedule.setNextDueDate(LocalDate.now().plusDays(calculateAdjustedFrequency(schedule)));
        careScheduleRepository.save(schedule);
        
        // Automated health adjustment based on care completion
        int healthDelta = 0;
        if (schedule.getCareType() == CareSchedule.CareType.WATERING) healthDelta = 2;
        if (schedule.getCareType() == CareSchedule.CareType.FERTILIZING) healthDelta = 5;
        
        if (healthDelta > 0) {
            healthService.adjustHealthScore(schedule.getPlant().getId(), healthDelta);
        }
    }
    
    public void deleteSchedule(Long scheduleId, Long userId) {
        CareSchedule schedule = careScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CareScheduleNotFoundException("Care schedule not found"));

        // Verify ownership
        plantRepository.findByIdAndUserId(schedule.getPlant().getId(), userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found or does not belong to user"));
                
        careScheduleRepository.delete(schedule);
    }

    private int calculateAdjustedFrequency(CareSchedule schedule) {
        int baseFrequency = schedule.getFrequencyDays();
        
        // Only adjust watering and misting by season
        if (schedule.getCareType() != CareSchedule.CareType.WATERING && 
            schedule.getCareType() != CareSchedule.CareType.MISTING) {
            return baseFrequency;
        }

        Month currentMonth = LocalDate.now().getMonth();
        double adjustmentFactor = 1.0;

        // Simple seasonal logic (Northern Hemisphere)
        switch (currentMonth) {
            case JUNE:
            case JULY:
            case AUGUST:
                adjustmentFactor = 0.7; // Summer: 30% more frequent
                break;
            case DECEMBER:
            case JANUARY:
            case FEBRUARY:
                adjustmentFactor = 1.5; // Winter: 50% less frequent
                break;
            default:
                adjustmentFactor = 1.0; // Spring/Autumn: Normal
                break;
        }

        return (int) Math.round(baseFrequency * adjustmentFactor);
    }
}




