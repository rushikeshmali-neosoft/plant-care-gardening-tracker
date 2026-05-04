package com.plantcare.knowledgebase.service;

import com.plantcare.knowledgebase.dto.CareGuideDto;
import com.plantcare.knowledgebase.entity.CareGuide;
import com.plantcare.knowledgebase.mapper.KnowledgeMapper;
import com.plantcare.knowledgebase.repository.CareGuideRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KnowledgeServiceTest {

    @Mock
    private CareGuideRepository careGuideRepository;

    @Mock
    private KnowledgeMapper knowledgeMapper;

    @InjectMocks
    private KnowledgeService knowledgeService;

    private CareGuide testGuide;
    private CareGuideDto testGuideDto;

    @BeforeEach
    void setUp() {
        testGuide = CareGuide.builder()
                .id(1L)
                .plantType("Monstera")
                .title("How to care for Monstera")
                .content("Water once a week. Indirect sunlight.")
                .createdAt(LocalDateTime.now())
                .build();

        testGuideDto = CareGuideDto.builder()
                .id(1L)
                .plantType("Monstera")
                .title("How to care for Monstera")
                .content("Water once a week. Indirect sunlight.")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void getAllGuides_ReturnsList() {
        when(careGuideRepository.findAll()).thenReturn(Collections.singletonList(testGuide));
        when(knowledgeMapper.toDto(testGuide)).thenReturn(testGuideDto);

        List<CareGuideDto> result = knowledgeService.getAllGuides();

        assertFalse(result.isEmpty());
        assertEquals("Monstera", result.get(0).getPlantType());
    }

    @Test
    void getGuidesByPlantType_ReturnsFilteredList() {
        when(careGuideRepository.findByPlantTypeContainingIgnoreCase("Monstera")).thenReturn(Collections.singletonList(testGuide));
        when(knowledgeMapper.toDto(testGuide)).thenReturn(testGuideDto);

        List<CareGuideDto> result = knowledgeService.getGuidesByPlantType("Monstera");

        assertFalse(result.isEmpty());
        assertEquals("How to care for Monstera", result.get(0).getTitle());
    }

    @Test
    void getGuideById_ReturnsGuide() {
        when(careGuideRepository.findById(1L)).thenReturn(Optional.of(testGuide));
        when(knowledgeMapper.toDto(testGuide)).thenReturn(testGuideDto);

        CareGuideDto result = knowledgeService.getGuideById(1L);

        assertNotNull(result);
        assertEquals("Monstera", result.getPlantType());
    }
}



