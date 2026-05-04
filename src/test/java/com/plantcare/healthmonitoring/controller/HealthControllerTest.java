package com.plantcare.healthmonitoring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plantcare.healthmonitoring.dto.CreateHealthIndicatorRequest;
import com.plantcare.healthmonitoring.dto.HealthIndicatorDto;
import com.plantcare.healthmonitoring.entity.HealthIndicator;
import com.plantcare.healthmonitoring.service.HealthService;
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
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthController.class)
@Import({SecurityConfig.class})
public class HealthControllerTest {

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
    private HealthService healthService;

    private User testUser;
    private CustomUserDetails userDetails;
    private HealthIndicatorDto testIndicatorDto;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .role(User.Role.ROLE_USER)
                .build();
                
        userDetails = new CustomUserDetails(testUser);

        testIndicatorDto = HealthIndicatorDto.builder()
                .id(1L)
                .plantId(1L)
                .healthStatus(HealthIndicator.HealthStatus.GOOD)
                .recordedDate(LocalDate.now())
                .build();
    }

    @Test
    void getHealthIndicators_Returns200() throws Exception {
        when(healthService.getHealthIndicators(1L, 1L)).thenReturn(Collections.singletonList(testIndicatorDto));

        mockMvc.perform(get("/api/v1/plants/1/health")
                .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].healthStatus").value("GOOD"));
    }

    @Test
    void addHealthIndicator_Returns201() throws Exception {
        CreateHealthIndicatorRequest request = new CreateHealthIndicatorRequest();
        request.setHealthStatus(HealthIndicator.HealthStatus.GOOD);
        request.setRecordedDate(LocalDate.now());

        when(healthService.addHealthIndicator(eq(1L), eq(1L), any(CreateHealthIndicatorRequest.class)))
                .thenReturn(testIndicatorDto);

        mockMvc.perform(post("/api/v1/plants/1/health")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.healthStatus").value("GOOD"));
    }
}



