package com.plantcare.healthmonitoring.service;

import com.plantcare.healthmonitoring.dto.CreateHealthIndicatorRequest;
import com.plantcare.healthmonitoring.dto.HealthIndicatorDto;
import com.plantcare.healthmonitoring.entity.HealthIndicator;
import com.plantcare.healthmonitoring.mapper.HealthMapper;
import com.plantcare.healthmonitoring.repository.HealthIndicatorRepository;
import com.plantcare.healthmonitoring.repository.SymptomLogRepository;
import com.plantcare.healthmonitoring.repository.TreatmentHistoryRepository;
import com.plantcare.healthmonitoring.repository.EnvironmentalFactorRepository;
import com.plantcare.healthmonitoring.repository.RecoveryRecordRepository;
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
public class HealthServiceTest {

@Mock
    private HealthIndicatorRepository healthIndicatorRepository;

    @Mock
    private SymptomLogRepository symptomLogRepository;

    @Mock
    private TreatmentHistoryRepository treatmentHistoryRepository;

    @Mock
    private EnvironmentalFactorRepository environmentalFactorRepository;

    @Mock
    private RecoveryRecordRepository recoveryRecordRepository;

    @Mock
    private PlantRepository plantRepository;

    @Mock
    private HealthMapper healthMapper;

    @Mock
    private org.springframework.messaging.simp.SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private HealthService healthService;

    private Plant testPlant;
    private HealthIndicator testIndicator;
    private HealthIndicatorDto testIndicatorDto;

    @BeforeEach
    void setUp() {
        testPlant = Plant.builder().id(1L).build();

        testIndicator = HealthIndicator.builder()
                .id(1L)
                .plant(testPlant)
                .healthStatus(HealthIndicator.HealthStatus.GOOD)
                .recordedDate(LocalDate.now())
                .build();

        testIndicatorDto = HealthIndicatorDto.builder()
                .id(1L)
                .plantId(1L)
                .healthStatus(HealthIndicator.HealthStatus.GOOD)
                .recordedDate(LocalDate.now())
                .build();
    }

    @Test
    void getHealthIndicators_ReturnsList() {
        when(plantRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testPlant));
        when(healthIndicatorRepository.findByPlantIdOrderByRecordedDateDesc(1L)).thenReturn(Collections.singletonList(testIndicator));
        when(healthMapper.toDto(testIndicator)).thenReturn(testIndicatorDto);

        List<HealthIndicatorDto> result = healthService.getHealthIndicators(1L, 1L);

        assertFalse(result.isEmpty());
        assertEquals(HealthIndicator.HealthStatus.GOOD, result.get(0).getHealthStatus());
    }

    @Test
    void addHealthIndicator_ReturnsCreatedIndicator() {
        CreateHealthIndicatorRequest request = new CreateHealthIndicatorRequest();
        request.setHealthStatus(HealthIndicator.HealthStatus.GOOD);
        request.setRecordedDate(LocalDate.now());

        when(plantRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testPlant));
        when(healthMapper.toEntity(request)).thenReturn(testIndicator);
        when(healthIndicatorRepository.save(any(HealthIndicator.class))).thenReturn(testIndicator);
        when(healthMapper.toDto(testIndicator)).thenReturn(testIndicatorDto);

        HealthIndicatorDto result = healthService.addHealthIndicator(1L, 1L, request);

        assertNotNull(result);
        assertEquals(HealthIndicator.HealthStatus.GOOD, result.getHealthStatus());
    }
}



