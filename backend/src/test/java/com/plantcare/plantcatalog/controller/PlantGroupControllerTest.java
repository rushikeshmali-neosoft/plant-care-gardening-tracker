package com.plantcare.plantcatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plantcare.plantcatalog.dto.CreateGroupRequest;
import com.plantcare.plantcatalog.dto.PlantGroupDto;
import com.plantcare.plantcatalog.service.GroupService;
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

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlantGroupController.class)
@Import({SecurityConfig.class})
public class PlantGroupControllerTest {

    @MockBean private JpaMetamodelMappingContext jpaMetamodelMappingContext;
    @MockBean private JwtService jwtService;
    @MockBean private CustomUserDetailsService customUserDetailsService;
    @MockBean private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private GroupService groupService;

    private User testUser;
    private CustomUserDetails userDetails;

    @BeforeEach
    void setUp() {
        testUser = User.builder().id(1L).email("test@example.com").role(User.Role.ROLE_USER).build();
        userDetails = new CustomUserDetails(testUser);
    }

    @Test
    void getUserGroups_Returns200() throws Exception {
        when(groupService.getUserGroups(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/groups")
                .with(user(userDetails)))
                .andExpect(status().isOk());
    }

    @Test
    void createGroup_Returns201() throws Exception {
        CreateGroupRequest request = new CreateGroupRequest("Group 1", "Desc");
        when(groupService.createGroup(eq(1L), any())).thenReturn(PlantGroupDto.builder().name("Group 1").build());

        mockMvc.perform(post("/api/v1/groups")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Group 1"));
    }
}



