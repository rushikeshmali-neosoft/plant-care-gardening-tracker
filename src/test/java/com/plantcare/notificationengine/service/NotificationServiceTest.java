package com.plantcare.notificationengine.service;

import com.plantcare.notificationengine.dto.NotificationDto;
import com.plantcare.notificationengine.entity.Notification;
import com.plantcare.notificationengine.mapper.NotificationMapper;
import com.plantcare.notificationengine.repository.NotificationRepository;
import com.plantcare.usermanagement.entity.User;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationMapper notificationMapper;

    @InjectMocks
    private NotificationService notificationService;

    private User testUser;
    private Notification testNotification;
    private NotificationDto testNotificationDto;

    @BeforeEach
    void setUp() {
        testUser = User.builder().id(1L).build();

        testNotification = Notification.builder()
                .id(1L)
                .user(testUser)
                .title("Watering Reminder")
                .message("Water your Monstera")
                .type(Notification.NotificationType.REMINDER)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        testNotificationDto = NotificationDto.builder()
                .id(1L)
                .userId(1L)
                .title("Watering Reminder")
                .message("Water your Monstera")
                .type(Notification.NotificationType.REMINDER)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void getUserNotifications_ReturnsList() {
        when(notificationRepository.findByUserIdOrderByCreatedAtDesc(1L)).thenReturn(Collections.singletonList(testNotification));
        when(notificationMapper.toDto(testNotification)).thenReturn(testNotificationDto);

        List<NotificationDto> result = notificationService.getUserNotifications(1L);

        assertFalse(result.isEmpty());
        assertEquals("Watering Reminder", result.get(0).getTitle());
    }

    @Test
    void markAsRead_UpdatesNotification() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(testNotification));
        
        notificationService.markAsRead(1L, 1L);
        
        assertTrue(testNotification.isRead());
        verify(notificationRepository).save(testNotification);
    }
}



