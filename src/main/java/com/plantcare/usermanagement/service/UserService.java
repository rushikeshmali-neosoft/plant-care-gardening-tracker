package com.plantcare.usermanagement.service;

import com.plantcare.usermanagement.dto.ProfileUpdateRequest;
import com.plantcare.usermanagement.dto.RegisterRequest;
import com.plantcare.usermanagement.entity.User;
import com.plantcare.usermanagement.exception.UserAlreadyExistsException;
import com.plantcare.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(RegisterRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Register request cannot be null");
        }

        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        // Create new user
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(User.Role.ROLE_USER)
                .experienceLevel(request.getExperienceLevel())
                .preferences("{}")
                .build();

        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User updateUserProfile(Long userId, ProfileUpdateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Profile update request cannot be null");
        }

        User user = getUserById(userId);
        
        // Update fields
        user.setName(request.getName());
        
        if (request.getExperienceLevel() != null) {
            user.setExperienceLevel(request.getExperienceLevel());
        }
        
        if (request.getPreferences() != null) {
            user.setPreferences(request.getPreferences());
        }
        
        return userRepository.save(user);
    }
}


