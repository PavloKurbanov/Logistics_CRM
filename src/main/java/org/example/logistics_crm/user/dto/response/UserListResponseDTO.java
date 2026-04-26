package org.example.logistics_crm.user.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.logistics_crm.user.UserRole;

public record UserListResponseDTO(
        @NotNull
        Long id,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotNull
        UserRole userRole
        ) {
}
