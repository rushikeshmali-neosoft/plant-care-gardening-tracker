package com.plantcare.carescheduling.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plantcare.carescheduling.dto.CareScheduleDto;
import com.plantcare.carescheduling.dto.CreateCareScheduleRequest;
import com.plantcare.carescheduling.entity.CareSchedule;
import com.plantcare.carescheduling.service.CareScheduleService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CareScheduleController.class)
@Import({SecurityConfig.class})
public class CareScheduleControllerTest {

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

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CareScheduleService careScheduleService;

    private User testUser;
    private CustomUserDetails userDetails;
    private CareScheduleDto testScheduleDto;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .role(User.Role.ROLE_USER)
                .build();
                
        userDetails = new CustomUserDetails(testUser);

        testScheduleDto = CareScheduleDto.builder()
                .id(1L)
                .plantId(1L)
                .careType(CareSchedule.CareType.WATERING)
                .frequencyDays(7)
                .nextDueDate(LocalDate.now().plusDays(7))
                .build();
    }

    @Test
    void getSchedulesForPlant_Returns200() throws Exception {
        when(careScheduleService.getSchedulesForPlant(1L, 1L))
                .thenReturn(Collections.singletonList(testScheduleDto));

        mockMvc.perform(get("/api/v1/plants/1/schedules")
                .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].careType").value("WATERING"));
    }

    @Test
    void createSchedule_Returns201() throws Exception {
        CreateCareScheduleRequest request = CreateCareScheduleRequest.builder()
                .careType(CareSchedule.CareType.WATERING)
                .frequencyDays(7)
                .build();

        when(careScheduleService.createSchedule(eq(1L), eq(1L), any(CreateCareScheduleRequest.class)))
                .thenReturn(testScheduleDto);

        mockMvc.perform(post("/api/v1/plants/1/schedules")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.careType").value("WATERING"));
    }

    @Test
    void markScheduleComplete_Returns200() throws Exception {
        doNothing().when(careScheduleService).markScheduleComplete(1L, 1L);

        mockMvc.perform(post("/api/v1/plants/1/schedules/1/complete")
                .with(user(userDetails)))
                .andExpect(status().isOk());
    }
}




