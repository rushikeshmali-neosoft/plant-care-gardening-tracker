package com.plantcare.plantcatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plantcare.plantcatalog.dto.CreatePlantRequest;
import com.plantcare.plantcatalog.dto.PlantDto;
import com.plantcare.plantcatalog.dto.UpdatePlantRequest;
import com.plantcare.plantcatalog.entity.Plant;
import com.plantcare.plantcatalog.service.PlantService;
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

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlantController.class)
@Import({SecurityConfig.class})
public class PlantControllerTest {

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
    private PlantService plantService;

    private User testUser;
    private CustomUserDetails userDetails;
    private PlantDto testPlantDto;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .role(User.Role.ROLE_USER)
                .build();
                
        userDetails = new CustomUserDetails(testUser);

        testPlantDto = PlantDto.builder()
                .id(1L)
                .commonName("Monstera")
                .scientificName("Monstera Deliciosa")
                .locationType(Plant.LocationType.INDOOR)
                .status(Plant.PlantStatus.ACTIVE)
                .build();
    }

    @Test
    void getAllPlants_Returns200() throws Exception {
        Page<PlantDto> page = new PageImpl<>(Collections.singletonList(testPlantDto));
        when(plantService.getAllPlantsForUser(eq(1L), any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/plants")
                .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].commonName").value("Monstera"));
    }

    @Test
    void getPlantById_Returns200() throws Exception {
        when(plantService.getPlantByIdAndUserId(1L, 1L)).thenReturn(testPlantDto);

        mockMvc.perform(get("/api/v1/plants/1")
                .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commonName").value("Monstera"));
    }

    @Test
    void createPlant_Returns201() throws Exception {
        CreatePlantRequest request = CreatePlantRequest.builder()
                .commonName("Monstera")
                .locationType(Plant.LocationType.INDOOR)
                .status(Plant.PlantStatus.ACTIVE)
                .build();

        when(plantService.createPlant(eq(1L), any(CreatePlantRequest.class))).thenReturn(testPlantDto);

        mockMvc.perform(post("/api/v1/plants")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.commonName").value("Monstera"));
    }

    @Test
    void updatePlant_Returns200() throws Exception {
        UpdatePlantRequest request = UpdatePlantRequest.builder()
                .commonName("Monstera Updated")
                .locationType(Plant.LocationType.INDOOR)
                .status(Plant.PlantStatus.ACTIVE)
                .build();

        when(plantService.updatePlant(eq(1L), eq(1L), any(UpdatePlantRequest.class))).thenReturn(testPlantDto);

        mockMvc.perform(put("/api/v1/plants/1")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deletePlant_Returns204() throws Exception {
        mockMvc.perform(delete("/api/v1/plants/1")
                .with(user(userDetails)))
                .andExpect(status().isNoContent());
    }
}



