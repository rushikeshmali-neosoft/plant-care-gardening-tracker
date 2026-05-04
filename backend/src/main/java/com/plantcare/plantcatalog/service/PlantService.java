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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlantService {

    private final PlantRepository plantRepository;
    private final UserRepository userRepository;
    private final PlantMapper plantMapper;

    public Page<PlantDto> getAllPlantsForUser(Long userId, Pageable pageable) {
        return plantRepository.findByUserId(userId, pageable)
                .map(plantMapper::toDto);
    }

    public PlantDto getPlantByIdAndUserId(Long id, Long userId) {
        Plant plant = plantRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found with id: " + id));
        return plantMapper.toDto(plant);
    }

    public PlantDto createPlant(Long userId, CreatePlantRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        Plant plant = plantMapper.toEntity(request);
        plant.setUser(user);
        
        Plant savedPlant = plantRepository.save(plant);
        return plantMapper.toDto(savedPlant);
    }

    public PlantDto updatePlant(Long id, Long userId, UpdatePlantRequest request) {
        Plant plant = plantRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found with id: " + id));
                
        plantMapper.updateEntityFromRequest(request, plant);
        
        Plant updatedPlant = plantRepository.save(plant);
        return plantMapper.toDto(updatedPlant);
    }

    public void deletePlant(Long id, Long userId) {
        Plant plant = plantRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found with id: " + id));
                
        plantRepository.delete(plant);
    }
}



