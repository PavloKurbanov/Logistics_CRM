package org.example.logistics_crm.user.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.logistics_crm.user.UserRole;

public record UserListResponseDTO(
        Long id,
        String firstName,
        String lastName,
        UserRole userRole
        ) {
}
