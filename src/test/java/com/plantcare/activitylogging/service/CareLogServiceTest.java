package com.plantcare.activitylogging.service;

import com.plantcare.activitylogging.dto.CareLogDto;
import com.plantcare.activitylogging.dto.CreateCareLogRequest;
import com.plantcare.activitylogging.entity.CareLog;
import com.plantcare.carescheduling.entity.CareSchedule;
import com.plantcare.activitylogging.mapper.CareLogMapper;
import com.plantcare.activitylogging.repository.CareLogRepository;
import com.plantcare.plantcatalog.entity.Plant;
import com.plantcare.plantcatalog.repository.PlantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CareLogServiceTest {

    @Mock
    private CareLogRepository careLogRepository;

    @Mock
    private PlantRepository plantRepository;

    @Mock
    private CareLogMapper careLogMapper;

    @InjectMocks
    private CareLogService careLogService;

    private Plant testPlant;
    private CareLog testLog;
    private CareLogDto testLogDto;
    private CreateCareLogRequest createRequest;

    @BeforeEach
    void setUp() {
        testPlant = Plant.builder()
                .id(1L)
                .commonName("Monstera")
                .build();

        testLog = CareLog.builder()
                .id(1L)
                .plant(testPlant)
                .careType(CareSchedule.CareType.WATERING)
                .logDate(LocalDateTime.now())
                .notes("Watered fully")
                .build();

        testLogDto = CareLogDto.builder()
                .id(1L)
                .plantId(1L)
                .careType(CareSchedule.CareType.WATERING)
                .logDate(LocalDateTime.now())
                .notes("Watered fully")
                .build();

        createRequest = CreateCareLogRequest.builder()
                .careType(CareSchedule.CareType.WATERING)
                .logDate(LocalDateTime.now())
                .notes("Watered fully")
                .build();
    }

    @Test
    void getLogsForPlant_ReturnsPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CareLog> page = new PageImpl<>(Collections.singletonList(testLog));
        
        when(plantRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testPlant));
        when(careLogRepository.findByPlantIdOrderByLogDateDesc(1L, pageable)).thenReturn(page);
        when(careLogMapper.toDto(testLog)).thenReturn(testLogDto);

        Page<CareLogDto> result = careLogService.getLogsForPlant(1L, 1L, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Watered fully", result.getContent().get(0).getNotes());
    }

    @Test
    void createLog_ReturnsCreatedLog() {
        when(plantRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testPlant));
        when(careLogMapper.toEntity(createRequest)).thenReturn(testLog);
        when(careLogRepository.save(any(CareLog.class))).thenReturn(testLog);
        when(careLogMapper.toDto(testLog)).thenReturn(testLogDto);

        CareLogDto result = careLogService.createLog(1L, 1L, createRequest);

        assertNotNull(result);
        assertEquals("Watered fully", result.getNotes());
        verify(careLogRepository).save(any(CareLog.class));
    }
}



