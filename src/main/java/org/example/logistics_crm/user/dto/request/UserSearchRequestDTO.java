package org.example.logistics_crm.user.dto.request;

import org.example.logistics_crm.user.UserRole;

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
