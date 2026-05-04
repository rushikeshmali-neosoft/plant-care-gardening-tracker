package com.plantcare.plantcatalog.service;

import com.plantcare.plantcatalog.dto.CreateGroupRequest;
import com.plantcare.plantcatalog.dto.PlantGroupDto;
import com.plantcare.plantcatalog.entity.Plant;
import com.plantcare.plantcatalog.entity.PlantGroup;
import com.plantcare.plantcatalog.mapper.GroupMapper;
import com.plantcare.plantcatalog.repository.PlantGroupRepository;
import com.plantcare.plantcatalog.repository.PlantRepository;
import com.plantcare.usermanagement.entity.User;
import com.plantcare.usermanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    @Mock private PlantGroupRepository groupRepository;
    @Mock private PlantRepository plantRepository;
    @Mock private UserRepository userRepository;
    @Mock private GroupMapper groupMapper;

    @InjectMocks private GroupService groupService;

    private User testUser;
    private PlantGroup testGroup;

    @BeforeEach
    void setUp() {
        testUser = User.builder().id(1L).build();
        testGroup = PlantGroup.builder()
                .id(1L)
                .user(testUser)
                .name("Indoor Plants")
                .plants(new HashSet<>())
                .build();
    }

    @Test
    void getUserGroups_ReturnsList() {
        when(groupRepository.findByUserId(1L)).thenReturn(Collections.singletonList(testGroup));
        when(groupMapper.toDto(any())).thenReturn(PlantGroupDto.builder().name("Indoor Plants").build());

        List<PlantGroupDto> result = groupService.getUserGroups(1L);

        assertFalse(result.isEmpty());
        assertEquals("Indoor Plants", result.get(0).getName());
    }

    @Test
    void createGroup_ReturnsCreatedGroup() {
        CreateGroupRequest request = new CreateGroupRequest("Indoor Plants", "Plants inside");
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(groupMapper.toEntity(request)).thenReturn(testGroup);
        when(groupRepository.save(any())).thenReturn(testGroup);
        when(groupMapper.toDto(testGroup)).thenReturn(PlantGroupDto.builder().name("Indoor Plants").build());

        PlantGroupDto result = groupService.createGroup(1L, request);

        assertNotNull(result);
        assertEquals("Indoor Plants", result.getName());
    }
}



