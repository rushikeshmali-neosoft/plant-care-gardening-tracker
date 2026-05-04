package com.plantcare.carescheduling.controller;

import com.plantcare.carescheduling.service.TaskService;
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

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@Import({SecurityConfig.class})
public class TaskControllerTest {

    @MockBean private JpaMetamodelMappingContext jpaMetamodelMappingContext;
    @MockBean private JwtService jwtService;
    @MockBean private CustomUserDetailsService customUserDetailsService;
    @MockBean private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired private MockMvc mockMvc;
    @MockBean private TaskService taskService;

    private User testUser;
    private CustomUserDetails userDetails;

    @BeforeEach
    void setUp() {
        testUser = User.builder().id(1L).email("test@example.com").role(User.Role.ROLE_USER).build();
        userDetails = new CustomUserDetails(testUser);
    }

    @Test
    void getUpcomingTasks_Returns200() throws Exception {
        when(taskService.getUpcomingTasks(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/tasks/upcoming")
                .with(user(userDetails)))
                .andExpect(status().isOk());
    }

    @Test
    void getOverdueTasks_Returns200() throws Exception {
        when(taskService.getOverdueTasks(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/tasks/overdue")
                .with(user(userDetails)))
                .andExpect(status().isOk());
    }
}




