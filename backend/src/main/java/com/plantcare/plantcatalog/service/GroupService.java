package com.plantcare.plantcatalog.service;

import com.plantcare.plantcatalog.dto.CreateGroupRequest;
import com.plantcare.plantcatalog.dto.PlantGroupDto;
import com.plantcare.plantcatalog.entity.Plant;
import com.plantcare.plantcatalog.entity.PlantGroup;
import com.plantcare.plantcatalog.exception.PlantNotFoundException;
import com.plantcare.plantcatalog.mapper.GroupMapper;
import com.plantcare.plantcatalog.repository.PlantGroupRepository;
import com.plantcare.plantcatalog.repository.PlantRepository;
import com.plantcare.usermanagement.entity.User;
import com.plantcare.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {

    private final PlantGroupRepository groupRepository;
    private final PlantRepository plantRepository;
    private final UserRepository userRepository;
    private final GroupMapper groupMapper;

    public List<PlantGroupDto> getUserGroups(Long userId) {
        return groupRepository.findByUserId(userId).stream()
                .map(groupMapper::toDto)
                .collect(Collectors.toList());
    }

    public PlantGroupDto createGroup(Long userId, CreateGroupRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PlantGroup group = groupMapper.toEntity(request);
        group.setUser(user);

        return groupMapper.toDto(groupRepository.save(group));
    }

    public void addPlantToGroup(Long groupId, Long plantId, Long userId) {
        PlantGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!group.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        Plant plant = plantRepository.findByIdAndUserId(plantId, userId)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found"));

        group.getPlants().add(plant);
        groupRepository.save(group);
    }

    public void removePlantFromGroup(Long groupId, Long plantId, Long userId) {
        PlantGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!group.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        group.getPlants().removeIf(p -> p.getId().equals(plantId));
        groupRepository.save(group);
    }

    public void deleteGroup(Long groupId, Long userId) {
        PlantGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!group.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        groupRepository.delete(group);
    }
}



