package com.plantcare.carescheduling.service;

import com.plantcare.carescheduling.dto.TaskDto;
import com.plantcare.carescheduling.entity.CareSchedule;
import com.plantcare.carescheduling.repository.CareScheduleRepository;
import com.plantcare.plantcatalog.entity.Plant;
import com.plantcare.usermanagement.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock private CareScheduleRepository scheduleRepository;
    @InjectMocks private TaskService taskService;

    private User testUser;
    private Plant testPlant;
    private CareSchedule testSchedule;

    @BeforeEach
    void setUp() {
        testUser = User.builder().id(1L).build();
        testPlant = Plant.builder().id(1L).user(testUser).commonName("Monstera").build();
        testSchedule = CareSchedule.builder()
                .id(1L)
                .plant(testPlant)
                .careType(CareSchedule.CareType.WATERING)
                .nextDueDate(LocalDate.now().plusDays(2))
                .build();
    }

    @Test
    void getUpcomingTasks_ReturnsList() {
        when(scheduleRepository.findAll()).thenReturn(Collections.singletonList(testSchedule));

        List<TaskDto> result = taskService.getUpcomingTasks(1L);

        assertFalse(result.isEmpty());
        assertEquals("Monstera", result.get(0).getPlantName());
        assertFalse(result.get(0).isOverdue());
    }

    @Test
    void getOverdueTasks_ReturnsList() {
        testSchedule.setNextDueDate(LocalDate.now().minusDays(1));
        when(scheduleRepository.findAll()).thenReturn(Collections.singletonList(testSchedule));

        List<TaskDto> result = taskService.getOverdueTasks(1L);

        assertFalse(result.isEmpty());
        assertTrue(result.get(0).isOverdue());
    }
}




