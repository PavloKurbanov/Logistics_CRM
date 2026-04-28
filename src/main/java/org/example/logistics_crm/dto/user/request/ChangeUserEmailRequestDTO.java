package org.example.logistics_crm.dto.user.request;

import jakarta.validation.constraints.NotBlank;

public record ChangeUserEmailRequestDTO(
        @NotBlank
        String currentPassword,

        @NotBlank
        String newEmail
) {
}
