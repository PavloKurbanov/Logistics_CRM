package org.example.logistics_crm.dto.client.request;

import jakarta.validation.constraints.NotBlank;

public record ChangeClientPasswordRequestDTO(
        @NotBlank
        String oldPassword,
        @NotBlank
        String newPassword,
        @NotBlank
        String confirmNewPassword) {
}
