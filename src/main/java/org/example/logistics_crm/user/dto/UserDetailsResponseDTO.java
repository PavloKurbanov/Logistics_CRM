package org.example.logistics_crm.user.dto;

import org.example.logistics_crm.user.UserRole;

import java.time.LocalDateTime;

public record UserDetailsResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        UserRole role,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
