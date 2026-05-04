package com.plantcare.carescheduling.service;

import com.plantcare.carescheduling.dto.TaskDto;
import com.plantcare.carescheduling.entity.CareSchedule;
import com.plantcare.carescheduling.repository.CareScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final CareScheduleRepository scheduleRepository;

    public List<TaskDto> getUpcomingTasks(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);

        return scheduleRepository.findAll().stream()
                .filter(s -> s.getPlant().getUser().getId().equals(userId))
                .filter(s -> s.getNextDueDate().isEqual(today) || (s.getNextDueDate().isAfter(today) && s.getNextDueDate().isBefore(nextWeek)))
                .map(this::toTaskDto)
                .collect(Collectors.toList());
    }

    public List<TaskDto> getOverdueTasks(Long userId) {
        LocalDate today = LocalDate.now();

        return scheduleRepository.findAll().stream()
                .filter(s -> s.getPlant().getUser().getId().equals(userId))
                .filter(s -> s.getNextDueDate().isBefore(today))
                .map(this::toTaskDto)
                .collect(Collectors.toList());
    }

    private TaskDto toTaskDto(CareSchedule schedule) {
        return TaskDto.builder()
                .scheduleId(schedule.getId())
                .plantId(schedule.getPlant().getId())
                .plantName(schedule.getPlant().getCommonName())
                .type(schedule.getCareType())
                .dueDate(schedule.getNextDueDate())
                .isOverdue(schedule.getNextDueDate().isBefore(LocalDate.now()))
                .build();
    }
}




