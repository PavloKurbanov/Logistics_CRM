package org.example.logistics_crm.user.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangeUserEmailRequestDTO(
        @NotBlank
        String currentPassword,

        @NotBlank
        String newEmail
) {
}
