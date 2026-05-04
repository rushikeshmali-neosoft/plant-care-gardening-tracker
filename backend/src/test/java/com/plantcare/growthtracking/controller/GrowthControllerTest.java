package com.plantcare.growthtracking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plantcare.growthtracking.dto.CreateMeasurementRequest;
import com.plantcare.growthtracking.dto.PlantMeasurementDto;
import com.plantcare.growthtracking.service.GrowthService;
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

@WebMvcTest(GrowthController.class)
@Import({SecurityConfig.class})
public class GrowthControllerTest {

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
    private GrowthService growthService;

    private User testUser;
    private CustomUserDetails userDetails;
    private PlantMeasurementDto testMeasurementDto;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .role(User.Role.ROLE_USER)
                .build();
                
        userDetails = new CustomUserDetails(testUser);

        testMeasurementDto = PlantMeasurementDto.builder()
                .id(1L)
                .plantId(1L)
                .heightCm(15.5)
                .measurementDate(LocalDate.now())
                .build();
    }

    @Test
    void getMeasurements_Returns200() throws Exception {
        when(growthService.getMeasurements(1L, 1L)).thenReturn(Collections.singletonList(testMeasurementDto));

        mockMvc.perform(get("/api/v1/plants/1/growth")
                .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].heightCm").value(15.5));
    }

    @Test
    void addMeasurement_Returns201() throws Exception {
        CreateMeasurementRequest request = new CreateMeasurementRequest();
        request.setHeightCm(15.5);
        request.setMeasurementDate(LocalDate.now());

        when(growthService.addMeasurement(eq(1L), eq(1L), any(CreateMeasurementRequest.class)))
                .thenReturn(testMeasurementDto);

        mockMvc.perform(post("/api/v1/plants/1/growth")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.heightCm").value(15.5));
    }
}



