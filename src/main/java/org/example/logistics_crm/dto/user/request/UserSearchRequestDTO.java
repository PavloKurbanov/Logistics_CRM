package org.example.logistics_crm.dto.user.request;

import org.example.logistics_crm.entity.user.UserRole;

import java.time.LocalDateTime;

public record UserSearchRequestDTO(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        UserRole userRole,
        LocalDateTime createdFrom,
        LocalDateTime createdTo
) {
}
