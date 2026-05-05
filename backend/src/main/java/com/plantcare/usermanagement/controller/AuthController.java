package com.plantcare.usermanagement.controller;

import com.plantcare.usermanagement.dto.AuthResponse;
import com.plantcare.usermanagement.dto.LoginRequest;
import com.plantcare.usermanagement.dto.RegisterRequest;
import com.plantcare.usermanagement.entity.User;
import com.plantcare.usermanagement.service.AuthService;
import com.plantcare.usermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication endpoints for user login, registration, and token management")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Operation(summary = "Register a new user", description = "Creates a new user account and returns a success response")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or validation failed"),
        @ApiResponse(responseCode = "409", description = "Email already registered")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletRequest httpRequest) {

        // Step 1: Register — saves user to DB
        User user = userService.registerUser(request);

        // Step 2: Auto-login so frontend gets tokens immediately (201 Created)
        LoginRequest loginRequest = LoginRequest.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        String ipAddress = getClientIpAddress(httpRequest);
        String userAgent = httpRequest.getHeader("User-Agent");

        AuthResponse authResponse = authService.login(loginRequest, ipAddress, userAgent);

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @Operation(summary = "User login", description = "Authenticates user credentials and returns JWT tokens")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "400", description = "Invalid credentials or input"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {

        String ipAddress = getClientIpAddress(httpRequest);
        String userAgent = httpRequest.getHeader("User-Agent");

        AuthResponse authResponse = authService.login(request, ipAddress, userAgent);

        return ResponseEntity.ok(authResponse);
    }

    @Operation(summary = "Refresh access token", description = "Generates new access and refresh tokens using a valid refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @RequestHeader("Authorization") String authorizationHeader,
            HttpServletRequest httpRequest) {

        String refreshToken = extractTokenFromHeader(authorizationHeader);
        String ipAddress = getClientIpAddress(httpRequest);
        String userAgent = httpRequest.getHeader("User-Agent");

        AuthResponse authResponse = authService.refreshToken(refreshToken, ipAddress, userAgent);

        return ResponseEntity.ok(authResponse);
    }

    @Operation(summary = "User logout", description = "Invalidates the current user session and tokens")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String authorizationHeader) {

        String accessToken = extractTokenFromHeader(authorizationHeader);
        authService.logout(accessToken);

        return ResponseEntity.ok().build();
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        throw new IllegalArgumentException("Invalid Authorization header format");
    }
}
