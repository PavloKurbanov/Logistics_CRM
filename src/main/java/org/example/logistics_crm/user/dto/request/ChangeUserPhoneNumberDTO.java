package org.example.logistics_crm.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChangeUserPhoneNumberDTO(
        @NotBlank
        String currentPassword,

        @NotBlank
        String newPhoneNumber
) {
}
