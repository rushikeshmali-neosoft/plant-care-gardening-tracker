package com.plantcare.activitylogging.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plantcare.activitylogging.dto.CareLogDto;
import com.plantcare.activitylogging.dto.CreateCareLogRequest;
import com.plantcare.carescheduling.entity.CareSchedule;
import com.plantcare.activitylogging.service.CareLogService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CareLogController.class)
@Import({SecurityConfig.class})
public class CareLogControllerTest {

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
    private CareLogService careLogService;

    private User testUser;
    private CustomUserDetails userDetails;
    private CareLogDto testLogDto;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .role(User.Role.ROLE_USER)
                .build();
                
        userDetails = new CustomUserDetails(testUser);

        testLogDto = CareLogDto.builder()
                .id(1L)
                .plantId(1L)
                .careType(CareSchedule.CareType.WATERING)
                .logDate(LocalDateTime.now())
                .notes("Watered fully")
                .build();
    }

    @Test
    void getLogsForPlant_Returns200() throws Exception {
        Page<CareLogDto> page = new PageImpl<>(Collections.singletonList(testLogDto));
        when(careLogService.getLogsForPlant(eq(1L), eq(1L), any(PageRequest.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/plants/1/logs")
                .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].notes").value("Watered fully"));
    }

    @Test
    void createLog_Returns201() throws Exception {
        CreateCareLogRequest request = CreateCareLogRequest.builder()
                .careType(CareSchedule.CareType.WATERING)
                .logDate(LocalDateTime.now())
                .notes("Watered fully")
                .build();

        when(careLogService.createLog(eq(1L), eq(1L), any(CreateCareLogRequest.class)))
                .thenReturn(testLogDto);

        mockMvc.perform(post("/api/v1/plants/1/logs")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.notes").value("Watered fully"));
    }
}



