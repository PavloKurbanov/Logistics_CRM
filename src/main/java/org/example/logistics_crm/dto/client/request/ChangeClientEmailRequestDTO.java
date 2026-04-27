package org.example.logistics_crm.dto.client.request;

import jakarta.validation.constraints.NotBlank;

public record ChangeClientEmailRequestDTO(
        @NotBlank
        String currentPassword,
        @NotBlank
        String newEmail
) {
}
