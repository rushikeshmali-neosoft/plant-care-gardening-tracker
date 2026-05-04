package com.plantcare.knowledgebase.controller;

import com.plantcare.knowledgebase.dto.CareGuideDto;
import com.plantcare.knowledgebase.service.KnowledgeService;
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

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KnowledgeController.class)
@Import({SecurityConfig.class})
public class KnowledgeControllerTest {

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
    private KnowledgeService knowledgeService;

    private User testUser;
    private CustomUserDetails userDetails;
    private CareGuideDto testGuideDto;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .role(User.Role.ROLE_USER)
                .build();
                
        userDetails = new CustomUserDetails(testUser);

        testGuideDto = CareGuideDto.builder()
                .id(1L)
                .plantType("Monstera")
                .title("How to care for Monstera")
                .content("Water once a week.")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void getAllGuides_Returns200() throws Exception {
        when(knowledgeService.getAllGuides()).thenReturn(Collections.singletonList(testGuideDto));

        mockMvc.perform(get("/api/v1/knowledge/guides")
                .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].plantType").value("Monstera"));
    }

    @Test
    void getGuidesByPlantType_Returns200() throws Exception {
        when(knowledgeService.getGuidesByPlantType("Monstera")).thenReturn(Collections.singletonList(testGuideDto));

        mockMvc.perform(get("/api/v1/knowledge/guides")
                .param("plantType", "Monstera")
                .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].plantType").value("Monstera"));
    }

    @Test
    void getGuideById_Returns200() throws Exception {
        when(knowledgeService.getGuideById(1L)).thenReturn(testGuideDto);

        mockMvc.perform(get("/api/v1/knowledge/guides/1")
                .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("How to care for Monstera"));
    }
}



