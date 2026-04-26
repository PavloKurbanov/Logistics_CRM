package org.example.logistics_crm.user.dto;

import org.example.logistics_crm.user.UserRole;

public record UserListResponseDTO(
        Long id,
        String firstName,
        String lastName,
        UserRole userRole
        ) {
}
