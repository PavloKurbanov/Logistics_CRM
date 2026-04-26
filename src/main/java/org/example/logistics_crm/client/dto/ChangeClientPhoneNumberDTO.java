package org.example.logistics_crm.client.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangeClientPhoneNumberDTO(
        @NotBlank
        String currentPassword,

        @NotBlank
        String newPhoneNumber
) {
}
