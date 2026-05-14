package com.plantcare.usermanagement.service;

import com.plantcare.sharedinfrastructure.exception.AuthenticationException;
import com.plantcare.usermanagement.dto.AuthResponse;
import com.plantcare.usermanagement.dto.LoginRequest;
import com.plantcare.usermanagement.entity.User;
import com.plantcare.usermanagement.entity.UserSession;
import com.plantcare.usermanagement.repository.UserRepository;
import com.plantcare.usermanagement.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request, String ipAddress, String userAgent) {
        if (request == null) {
            throw new IllegalArgumentException("Login request cannot be null");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthenticationException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new AuthenticationException("Invalid email or password");
        }

        // Generate tokens
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Create user session
        UserSession session = UserSession.builder()
                .user(user)
                .sessionToken(accessToken)
                .refreshToken(refreshToken)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .expiresAt(LocalDateTime.now().plusDays(7)) // Refresh token expiry
                .active(true)
                .build();

        userSessionRepository.save(session);

        // Build user response
        AuthResponse.UserResponse userResponse = AuthResponse.UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .experienceLevel(user.getExperienceLevel())
                .build();

        // Build auth response
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(900L) // 15 minutes in seconds
                .user(userResponse)
                .build();
    }

    public void logout(String sessionToken) {
        userSessionRepository.findBySessionToken(sessionToken)
                .ifPresent(session -> {
                    session.setActive(false);
                    userSessionRepository.save(session);
                });
    }

    public AuthResponse refreshToken(String refreshToken, String ipAddress, String userAgent) {
        // Validate refresh token
        String email = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        // Find active session with this refresh token
        UserSession session = userSessionRepository.findByRefreshTokenAndActiveTrue(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid or expired refresh token"));

        // Generate new tokens
        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        // Update session
        session.setSessionToken(newAccessToken);
        session.setRefreshToken(newRefreshToken);
        session.setLastAccessedAt(LocalDateTime.now());
        session.setIpAddress(ipAddress);
        session.setUserAgent(userAgent);
        userSessionRepository.save(session);

        // Build user response
        AuthResponse.UserResponse userResponse = AuthResponse.UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .experienceLevel(user.getExperienceLevel())
                .build();

        // Build auth response
        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .expiresIn(900L)
                .user(userResponse)
                .build();
    }
}


