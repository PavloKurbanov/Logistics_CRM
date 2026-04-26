package org.example.logistics_crm.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ClientDetailsResponseDTO(
        @NotNull
        Long id,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotNull
        @Email
        String email,
        @NotBlank
        String phoneNumber,
        @NotNull
        LocalDateTime createdDate,
        @NotNull
        LocalDateTime updatedDate
) {
}
