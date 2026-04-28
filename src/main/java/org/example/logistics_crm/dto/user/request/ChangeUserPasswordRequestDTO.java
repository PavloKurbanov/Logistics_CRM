package org.example.logistics_crm.dto.user.request;

import jakarta.validation.constraints.NotBlank;

public record ChangeUserPasswordRequestDTO(
        @NotBlank
        String oldPassword,
        @NotBlank
        String newPassword,
        @NotBlank
        String confirmNewPassword
) {
}
