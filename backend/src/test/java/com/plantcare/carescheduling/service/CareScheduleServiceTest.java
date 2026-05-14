package com.plantcare.carescheduling.service;

import com.plantcare.carescheduling.dto.CareScheduleDto;
import com.plantcare.carescheduling.dto.CreateCareScheduleRequest;
import com.plantcare.carescheduling.entity.CareSchedule;
import com.plantcare.carescheduling.exception.CareScheduleNotFoundException;
import com.plantcare.carescheduling.mapper.CareScheduleMapper;
import com.plantcare.carescheduling.repository.CareScheduleRepository;
import com.plantcare.plantcatalog.entity.Plant;
import com.plantcare.plantcatalog.repository.PlantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CareScheduleServiceTest {

    @Mock
    private CareScheduleRepository careScheduleRepository;

    @Mock
    private PlantRepository plantRepository;

@Mock
    private CareScheduleMapper careScheduleMapper;

    @Mock
    private com.plantcare.healthmonitoring.service.HealthService healthService;

    @Mock
    private org.springframework.messaging.simp.SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private CareScheduleService careScheduleService;

    private Plant testPlant;
    private CareSchedule testSchedule;
    private CareScheduleDto testScheduleDto;
    private CreateCareScheduleRequest createRequest;

    @BeforeEach
    void setUp() {
        testPlant = Plant.builder()
                .id(1L)
                .commonName("Monstera")
                .build();

        testSchedule = CareSchedule.builder()
                .id(1L)
                .plant(testPlant)
                .careType(CareSchedule.CareType.WATERING)
                .frequencyDays(7)
                .nextDueDate(LocalDate.now().plusDays(7))
                .build();

        testScheduleDto = CareScheduleDto.builder()
                .id(1L)
                .plantId(1L)
                .careType(CareSchedule.CareType.WATERING)
                .frequencyDays(7)
                .nextDueDate(LocalDate.now().plusDays(7))
                .build();

        createRequest = CreateCareScheduleRequest.builder()
                .careType(CareSchedule.CareType.WATERING)
                .frequencyDays(7)
                .build();
    }

    @Test
    void getSchedulesForPlant_ReturnsList() {
        when(plantRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testPlant));
        when(careScheduleRepository.findByPlantId(1L)).thenReturn(Collections.singletonList(testSchedule));
        when(careScheduleMapper.toDto(testSchedule)).thenReturn(testScheduleDto);

        List<CareScheduleDto> result = careScheduleService.getSchedulesForPlant(1L, 1L);

        assertFalse(result.isEmpty());
        assertEquals(CareSchedule.CareType.WATERING, result.get(0).getCareType());
    }

    @Test
    void createSchedule_ReturnsCreatedSchedule() {
        when(plantRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testPlant));
        when(careScheduleMapper.toEntity(createRequest)).thenReturn(testSchedule);
        when(careScheduleRepository.save(any(CareSchedule.class))).thenReturn(testSchedule);
        when(careScheduleMapper.toDto(testSchedule)).thenReturn(testScheduleDto);

        CareScheduleDto result = careScheduleService.createSchedule(1L, 1L, createRequest);

        assertNotNull(result);
        assertEquals(CareSchedule.CareType.WATERING, result.getCareType());
        verify(careScheduleRepository).save(any(CareSchedule.class));
    }

    @Test
    void markScheduleComplete_UpdatesNextDueDate() {
        when(careScheduleRepository.findById(1L)).thenReturn(Optional.of(testSchedule));
        when(plantRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testPlant));
        when(careScheduleRepository.save(any(CareSchedule.class))).thenReturn(testSchedule);
        
        careScheduleService.markScheduleComplete(1L, 1L);
        
        verify(careScheduleRepository).save(testSchedule);
    }
}




