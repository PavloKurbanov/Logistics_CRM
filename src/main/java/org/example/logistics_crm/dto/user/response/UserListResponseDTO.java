package org.example.logistics_crm.dto.user.response;

import org.example.logistics_crm.entity.user.UserRole;

public record UserListResponseDTO(
        Long id,
        String firstName,
        String lastName,
        UserRole userRole,
        String phoneNumber,
        String email
        ) {
}
