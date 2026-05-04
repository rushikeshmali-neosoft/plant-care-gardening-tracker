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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CareScheduleService {

    private final CareScheduleRepository careScheduleRepository;
    private final PlantRepository plantRepository;
    private final CareScheduleMapper careScheduleMapper;

    public List<CareScheduleDto> getSchedulesForPlant(Long plantId, Long userId) {
        // Ensure the plant belongs to the user
        Plant plant = plantRepository.findByIdAndUserId(plantId, userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found or does not belong to user"));

        return careScheduleRepository.findByPlantId(plantId).stream()
                .map(careScheduleMapper::toDto)
                .collect(Collectors.toList());
    }

    public CareScheduleDto createSchedule(Long plantId, Long userId, CreateCareScheduleRequest request) {
        Plant plant = plantRepository.findByIdAndUserId(plantId, userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found or does not belong to user"));

        CareSchedule schedule = careScheduleMapper.toEntity(request);
        schedule.setPlant(plant);
        
        if (schedule.getNextDueDate() == null) {
            schedule.setNextDueDate(LocalDate.now().plusDays(schedule.getFrequencyDays()));
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

        // Update the next due date based on frequency
        schedule.setNextDueDate(LocalDate.now().plusDays(schedule.getFrequencyDays()));
        careScheduleRepository.save(schedule);
        
        // Activity logging will be added here in Phase 3
    }
    
    public void deleteSchedule(Long scheduleId, Long userId) {
        CareSchedule schedule = careScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CareScheduleNotFoundException("Care schedule not found"));

        // Verify ownership
        plantRepository.findByIdAndUserId(schedule.getPlant().getId(), userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found or does not belong to user"));
                
        careScheduleRepository.delete(schedule);
    }
}




