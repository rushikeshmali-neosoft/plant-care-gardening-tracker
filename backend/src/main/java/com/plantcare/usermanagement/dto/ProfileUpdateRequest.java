package com.plantcare.usermanagement.dto;

import com.plantcare.usermanagement.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request for updating user profile")
public class ProfileUpdateRequest {

    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "User's full name", example = "John Doe", required = false)
    private String name;

    @Schema(description = "User's gardening experience level", example = "INTERMEDIATE")
    private User.ExperienceLevel experienceLevel;

    @Schema(description = "User preferences in JSON format", example = "{\"notifications\": true, \"theme\": \"dark\"}")
    private String preferences;
}


