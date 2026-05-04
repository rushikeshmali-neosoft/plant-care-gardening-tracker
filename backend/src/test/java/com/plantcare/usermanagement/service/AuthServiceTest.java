package com.plantcare.usermanagement.service;

import com.plantcare.usermanagement.dto.AuthResponse;
import com.plantcare.usermanagement.dto.LoginRequest;
import com.plantcare.usermanagement.entity.User;
import com.plantcare.usermanagement.entity.UserSession;
import com.plantcare.usermanagement.repository.UserRepository;
import com.plantcare.usermanagement.repository.UserSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserSessionRepository userSessionRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private User testUser;
    private LoginRequest validLoginRequest;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .passwordHash("encodedPassword")
                .role(User.Role.ROLE_USER)
                .experienceLevel(User.ExperienceLevel.BEGINNER)
                .build();

        validLoginRequest = LoginRequest.builder()
                .email("john@example.com")
                .password("Password123!")
                .build();
    }

    @Test
    void login_WithValidCredentials_ShouldReturnAuthResponse() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtService.generateAccessToken(any(User.class))).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");
        when(userSessionRepository.save(any(UserSession.class))).thenAnswer(invocation -> {
            UserSession session = invocation.getArgument(0);
            session.setId(1L);
            return session;
        });

        // Act
        AuthResponse response = authService.login(validLoginRequest, "127.0.0.1", "Test Browser");

        // Assert
        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        assertEquals("Bearer", response.getTokenType());
        assertNotNull(response.getUser());
        assertEquals(1L, response.getUser().getId());
        assertEquals("John Doe", response.getUser().getName());
        assertEquals("john@example.com", response.getUser().getEmail());

        verify(userRepository).findByEmail("john@example.com");
        verify(passwordEncoder).matches("Password123!", "encodedPassword");
        verify(jwtService).generateAccessToken(testUser);
        verify(jwtService).generateRefreshToken(testUser);
        verify(userSessionRepository).save(any(UserSession.class));
    }

    @Test
    void login_WithInvalidEmail_ShouldThrowException() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            authService.login(validLoginRequest, "127.0.0.1", "Test Browser");
        });

        verify(userRepository).findByEmail("john@example.com");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtService, never()).generateAccessToken(any());
        verify(userSessionRepository, never()).save(any());
    }

    @Test
    void login_WithInvalidPassword_ShouldThrowException() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            authService.login(validLoginRequest, "127.0.0.1", "Test Browser");
        });

        verify(userRepository).findByEmail("john@example.com");
        verify(passwordEncoder).matches("Password123!", "encodedPassword");
        verify(jwtService, never()).generateAccessToken(any());
        verify(userSessionRepository, never()).save(any());
    }
}


