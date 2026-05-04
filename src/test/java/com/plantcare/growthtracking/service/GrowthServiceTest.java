package com.plantcare.growthtracking.service;

import com.plantcare.growthtracking.dto.CreateMeasurementRequest;
import com.plantcare.growthtracking.dto.PlantMeasurementDto;
import com.plantcare.growthtracking.entity.PlantMeasurement;
import com.plantcare.growthtracking.mapper.GrowthMapper;
import com.plantcare.growthtracking.repository.PlantMeasurementRepository;
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
public class GrowthServiceTest {

    @Mock
    private PlantMeasurementRepository measurementRepository;

    @Mock
    private PlantRepository plantRepository;

    @Mock
    private GrowthMapper growthMapper;

    @InjectMocks
    private GrowthService growthService;

    private Plant testPlant;
    private PlantMeasurement testMeasurement;
    private PlantMeasurementDto testMeasurementDto;

    @BeforeEach
    void setUp() {
        testPlant = Plant.builder()
                .id(1L)
                .build();

        testMeasurement = PlantMeasurement.builder()
                .id(1L)
                .plant(testPlant)
                .heightCm(15.5)
                .measurementDate(LocalDate.now())
                .build();

        testMeasurementDto = PlantMeasurementDto.builder()
                .id(1L)
                .plantId(1L)
                .heightCm(15.5)
                .measurementDate(LocalDate.now())
                .build();
    }

    @Test
    void getMeasurements_ReturnsList() {
        when(plantRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testPlant));
        when(measurementRepository.findByPlantIdOrderByMeasurementDateDesc(1L)).thenReturn(Collections.singletonList(testMeasurement));
        when(growthMapper.toDto(testMeasurement)).thenReturn(testMeasurementDto);

        List<PlantMeasurementDto> result = growthService.getMeasurements(1L, 1L);

        assertFalse(result.isEmpty());
        assertEquals(15.5, result.get(0).getHeightCm());
    }

    @Test
    void addMeasurement_ReturnsCreatedMeasurement() {
        CreateMeasurementRequest request = new CreateMeasurementRequest();
        request.setHeightCm(15.5);
        request.setMeasurementDate(LocalDate.now());

        when(plantRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testPlant));
        when(growthMapper.toEntity(request)).thenReturn(testMeasurement);
        when(measurementRepository.save(any(PlantMeasurement.class))).thenReturn(testMeasurement);
        when(growthMapper.toDto(testMeasurement)).thenReturn(testMeasurementDto);

        PlantMeasurementDto result = growthService.addMeasurement(1L, 1L, request);

        assertNotNull(result);
        assertEquals(15.5, result.getHeightCm());
    }
}



