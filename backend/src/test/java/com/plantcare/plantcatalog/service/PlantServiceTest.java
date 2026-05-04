package com.plantcare.plantcatalog.service;

import com.plantcare.plantcatalog.dto.CreatePlantRequest;
import com.plantcare.plantcatalog.dto.PlantDto;
import com.plantcare.plantcatalog.dto.UpdatePlantRequest;
import com.plantcare.plantcatalog.entity.Plant;
import com.plantcare.plantcatalog.exception.PlantNotFoundException;
import com.plantcare.plantcatalog.mapper.PlantMapper;
import com.plantcare.plantcatalog.repository.PlantRepository;
import com.plantcare.usermanagement.entity.User;
import com.plantcare.usermanagement.repository.UserRepository;
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

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlantServiceTest {

    @Mock
    private PlantRepository plantRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlantMapper plantMapper;

    @InjectMocks
    private PlantService plantService;

    private User testUser;
    private Plant testPlant;
    private PlantDto testPlantDto;
    private CreatePlantRequest createPlantRequest;
    private UpdatePlantRequest updatePlantRequest;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .build();

        testPlant = Plant.builder()
                .id(1L)
                .commonName("Monstera")
                .scientificName("Monstera Deliciosa")
                .locationType(Plant.LocationType.INDOOR)
                .status(Plant.PlantStatus.ACTIVE)
                .user(testUser)
                .build();

        testPlantDto = PlantDto.builder()
                .id(1L)
                .commonName("Monstera")
                .scientificName("Monstera Deliciosa")
                .locationType(Plant.LocationType.INDOOR)
                .status(Plant.PlantStatus.ACTIVE)
                .build();

        createPlantRequest = CreatePlantRequest.builder()
                .commonName("Monstera")
                .scientificName("Monstera Deliciosa")
                .locationType(Plant.LocationType.INDOOR)
                .status(Plant.PlantStatus.ACTIVE)
                .build();

        updatePlantRequest = UpdatePlantRequest.builder()
                .commonName("Monstera Updated")
                .locationType(Plant.LocationType.INDOOR)
                .status(Plant.PlantStatus.ACTIVE)
                .build();
    }

    @Test
    void getAllPlantsForUser_ReturnsPageOfPlants() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Plant> plantPage = new PageImpl<>(Collections.singletonList(testPlant));
        
        when(plantRepository.findByUserId(1L, pageable)).thenReturn(plantPage);
        when(plantMapper.toDto(testPlant)).thenReturn(testPlantDto);

        Page<PlantDto> result = plantService.getAllPlantsForUser(1L, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Monstera", result.getContent().get(0).getCommonName());
    }

    @Test
    void getPlantByIdAndUserId_WhenPlantExists_ReturnsPlant() {
        when(plantRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testPlant));
        when(plantMapper.toDto(testPlant)).thenReturn(testPlantDto);

        PlantDto result = plantService.getPlantByIdAndUserId(1L, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Monstera", result.getCommonName());
    }

    @Test
    void getPlantByIdAndUserId_WhenPlantDoesNotExist_ThrowsException() {
        when(plantRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(PlantNotFoundException.class, () -> plantService.getPlantByIdAndUserId(1L, 1L));
    }

    @Test
    void createPlant_WhenUserExists_ReturnsCreatedPlant() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(plantMapper.toEntity(createPlantRequest)).thenReturn(testPlant);
        when(plantRepository.save(any(Plant.class))).thenReturn(testPlant);
        when(plantMapper.toDto(testPlant)).thenReturn(testPlantDto);

        PlantDto result = plantService.createPlant(1L, createPlantRequest);

        assertNotNull(result);
        assertEquals("Monstera", result.getCommonName());
        verify(plantRepository).save(any(Plant.class));
    }

    @Test
    void updatePlant_WhenPlantExists_ReturnsUpdatedPlant() {
        when(plantRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testPlant));
        when(plantRepository.save(any(Plant.class))).thenReturn(testPlant);
        when(plantMapper.toDto(testPlant)).thenReturn(testPlantDto);
        doNothing().when(plantMapper).updateEntityFromRequest(updatePlantRequest, testPlant);

        PlantDto result = plantService.updatePlant(1L, 1L, updatePlantRequest);

        assertNotNull(result);
        verify(plantMapper).updateEntityFromRequest(updatePlantRequest, testPlant);
        verify(plantRepository).save(testPlant);
    }

    @Test
    void deletePlant_WhenPlantExists_DeletesPlant() {
        when(plantRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testPlant));
        doNothing().when(plantRepository).delete(testPlant);

        assertDoesNotThrow(() -> plantService.deletePlant(1L, 1L));
        verify(plantRepository).delete(testPlant);
    }
}



