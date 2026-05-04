package com.plantcare.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plantcare.sharedinfrastructure.security.CustomUserDetails;
import com.plantcare.sharedinfrastructure.security.JwtAuthenticationEntryPoint;
import com.plantcare.sharedinfrastructure.security.SecurityConfig;
import com.plantcare.usermanagement.dto.ProfileUpdateRequest;
import com.plantcare.usermanagement.entity.User;
import com.plantcare.usermanagement.service.JwtService;
import com.plantcare.sharedinfrastructure.security.CustomUserDetailsService;
import com.plantcare.usermanagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({SecurityConfig.class})
public class UserControllerTest {

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
    private UserService userService;

    private User testUser;
    private CustomUserDetails userDetails;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .name("Test User")
                .role(User.Role.ROLE_USER)
                .experienceLevel(User.ExperienceLevel.INTERMEDIATE)
                .preferences("{\"notifications\": true}")
                .build();
                
        userDetails = new CustomUserDetails(testUser);
    }

    @Test
    void getProfile_Returns200() throws Exception {
        when(userService.getUserByEmail("test@example.com")).thenReturn(testUser);

        mockMvc.perform(get("/api/v1/users/profile")
                .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void updateProfile_Returns200() throws Exception {
        ProfileUpdateRequest request = ProfileUpdateRequest.builder()
                .name("Updated Name")
                .experienceLevel(User.ExperienceLevel.ADVANCED)
                .build();

        User updatedUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .name("Updated Name")
                .experienceLevel(User.ExperienceLevel.ADVANCED)
                .build();

        when(userService.getUserByEmail("test@example.com")).thenReturn(testUser);
        when(userService.updateUserProfile(eq(1L), any(ProfileUpdateRequest.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/v1/users/profile")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    void getPreferences_Returns200() throws Exception {
        when(userService.getUserByEmail("test@example.com")).thenReturn(testUser);

        mockMvc.perform(get("/api/v1/users/preferences")
                .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().string("{\"notifications\": true}"));
    }
}



