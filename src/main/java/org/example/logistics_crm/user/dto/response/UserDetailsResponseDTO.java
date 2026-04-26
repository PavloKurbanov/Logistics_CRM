package org.example.logistics_crm.user.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.logistics_crm.user.UserRole;

import java.time.LocalDateTime;

public record UserDetailsResponseDTO(
        @NotNull
        Long id,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String phoneNumber,
        @NotNull
        UserRole role,
        @NotNull
        LocalDateTime createdDate,
        @NotNull
        LocalDateTime updatedDate
) {
}
