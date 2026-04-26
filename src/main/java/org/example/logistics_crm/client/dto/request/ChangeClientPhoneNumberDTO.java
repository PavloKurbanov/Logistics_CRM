package org.example.logistics_crm.client.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChangeClientPhoneNumberDTO(
        @NotBlank
        String currentPassword,

        @NotBlank
        String newPhoneNumber
) {
}
