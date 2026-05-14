package com.plantcare.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plantcare.sharedinfrastructure.exception.AuthenticationException;
import com.plantcare.sharedinfrastructure.security.JwtAuthenticationEntryPoint;
import com.plantcare.sharedinfrastructure.security.SecurityConfig;
import com.plantcare.usermanagement.dto.AuthResponse;
import com.plantcare.usermanagement.dto.LoginRequest;
import com.plantcare.usermanagement.dto.RegisterRequest;
import com.plantcare.usermanagement.entity.User;
import com.plantcare.usermanagement.service.AuthService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import({SecurityConfig.class})
class AuthControllerTest {

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
    private AuthService authService;

    @MockBean
    private UserService userService;

    private RegisterRequest validRegisterRequest;
    private LoginRequest validLoginRequest;
    private AuthResponse mockAuthResponse;
    private User mockUser;

    @BeforeEach
    void setUp() {
        validRegisterRequest = RegisterRequest.builder()
                .name("John Doe")
                .email("john@example.com")
                .password("Password123!")
                .build();

        validLoginRequest = LoginRequest.builder()
                .email("john@example.com")
                .password("Password123!")
                .build();

        mockUser = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .role(User.Role.ROLE_USER)
                .experienceLevel(User.ExperienceLevel.BEGINNER)
                .build();

        AuthResponse.UserResponse userResponse = AuthResponse.UserResponse.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .role(User.Role.ROLE_USER)
                .experienceLevel(User.ExperienceLevel.BEGINNER)
                .build();

        mockAuthResponse = AuthResponse.builder()
                .accessToken("access-token-123")
                .refreshToken("refresh-token-456")
                .tokenType("Bearer")
                .expiresIn(900L)
                .user(userResponse)
                .build();
    }

    @Test
    void register_WithValidRequest_ShouldReturnCreated() throws Exception {
        when(userService.registerUser(any(RegisterRequest.class))).thenReturn(mockUser);
        when(authService.login(any(LoginRequest.class), any(), any()))
                .thenReturn(mockAuthResponse);

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRegisterRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").value("access-token-123"))
                .andExpect(jsonPath("$.user.name").value("John Doe"));
    }

    @Test
    void register_WithInvalidRequest_ShouldReturnBadRequest() throws Exception {
        RegisterRequest invalidRequest = RegisterRequest.builder()
                .name("")
                .email("invalid-email")
                .password("short")
                .build();

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_WithValidCredentials_ShouldReturnOk() throws Exception {
        when(authService.login(any(LoginRequest.class), any(), any()))
                .thenReturn(mockAuthResponse);

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLoginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token-123"));
    }

@Test
    void login_WithInvalidCredentials_ShouldThrowException() throws Exception {
        when(authService.login(any(LoginRequest.class), any(), any()))
                .thenThrow(new AuthenticationException("Invalid email or password"));

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLoginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void refreshToken_WithValidRefreshToken_ShouldReturnOk() throws Exception {
        when(authService.refreshToken(anyString(), any(), any()))
                .thenReturn(mockAuthResponse);

        mockMvc.perform(post("/api/v1/auth/refresh")
                .header("Authorization", "Bearer valid-refresh-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token-123"));
    }

    @Test
    void refreshToken_WithInvalidHeaderFormat_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/auth/refresh")
                .header("Authorization", "InvalidFormat"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void logout_WithValidToken_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/v1/auth/logout")
                .header("Authorization", "Bearer valid-access-token"))
                .andExpect(status().isOk());
    }
}


