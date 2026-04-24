package org.example.logistics_crm.client.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangePhoneNumberDTO(
        @NotBlank
        String currentPassword,

        @NotBlank
        String newPhoneNumber
) {
}
