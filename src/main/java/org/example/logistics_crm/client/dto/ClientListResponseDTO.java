package org.example.logistics_crm.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientListResponseDTO(
        @NotNull
        Long id,
        @NotBlank
        String firstName,
        @NotBlank
        String LastName
) {
}
