package org.example.logistics_crm.client.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangeEmailRequestDTO(
        @NotBlank
        String currentPassword,

        @NotBlank
        String newEmail
) {
}
