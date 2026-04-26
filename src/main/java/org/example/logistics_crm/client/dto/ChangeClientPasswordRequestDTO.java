package org.example.logistics_crm.client.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangeClientPasswordRequestDTO(
        @NotBlank
        String oldPassword,
        @NotBlank
        String newPassword,
        @NotBlank
        String confirmNewPassword) {
}
