package com.plantcare.notificationengine.controller;

import com.plantcare.notificationengine.dto.NotificationDto;
import com.plantcare.notificationengine.entity.Notification;
import com.plantcare.notificationengine.service.NotificationService;
import com.plantcare.sharedinfrastructure.security.CustomUserDetails;
import com.plantcare.sharedinfrastructure.security.JwtAuthenticationEntryPoint;
import com.plantcare.sharedinfrastructure.security.SecurityConfig;
import com.plantcare.usermanagement.entity.User;
import com.plantcare.usermanagement.service.JwtService;
import com.plantcare.sharedinfrastructure.security.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
@Import({SecurityConfig.class})
public class NotificationControllerTest {

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    private User testUser;
    private CustomUserDetails userDetails;
    private NotificationDto testNotificationDto;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .role(User.Role.ROLE_USER)
                .build();
                
        userDetails = new CustomUserDetails(testUser);

        testNotificationDto = NotificationDto.builder()
                .id(1L)
                .userId(1L)
                .title("Watering Reminder")
                .message("Water your Monstera")
                .type(Notification.NotificationType.REMINDER)
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void getUserNotifications_Returns200() throws Exception {
        when(notificationService.getUserNotifications(1L)).thenReturn(Collections.singletonList(testNotificationDto));

        mockMvc.perform(get("/api/v1/notifications")
                .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Watering Reminder"));
    }

    @Test
    void markAsRead_Returns200() throws Exception {
        doNothing().when(notificationService).markAsRead(1L, 1L);

        mockMvc.perform(put("/api/v1/notifications/1/read")
                .with(user(userDetails)))
                .andExpect(status().isOk());
    }
}



