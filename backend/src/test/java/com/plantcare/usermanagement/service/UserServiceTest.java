package com.plantcare.usermanagement.service;

import com.plantcare.usermanagement.dto.RegisterRequest;
import com.plantcare.usermanagement.entity.User;
import com.plantcare.usermanagement.exception.UserAlreadyExistsException;
import com.plantcare.usermanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private RegisterRequest validRegisterRequest;

    @BeforeEach
    void setUp() {
        validRegisterRequest = RegisterRequest.builder()
                .name("John Doe")
                .email("john@example.com")
                .password("Password123!")
                .experienceLevel(User.ExperienceLevel.BEGINNER)
                .build();
    }

    @Test
    void registerUser_WithValidRequest_ShouldReturnUser() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        // Act
        User result = userService.registerUser(validRegisterRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getPasswordHash());
        assertEquals(User.Role.ROLE_USER, result.getRole());
        assertEquals(User.ExperienceLevel.BEGINNER, result.getExperienceLevel());

        verify(userRepository).existsByEmail("john@example.com");
        verify(passwordEncoder).encode("Password123!");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_WithDuplicateEmail_ShouldThrowException() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.registerUser(validRegisterRequest);
        });

        verify(userRepository).existsByEmail("john@example.com");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_WithNullRequest_ShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(null);
        });
    }
}


