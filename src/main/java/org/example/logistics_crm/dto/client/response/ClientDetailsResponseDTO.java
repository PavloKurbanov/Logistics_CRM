package org.example.logistics_crm.dto.client.response;

import java.time.LocalDateTime;

public record ClientDetailsResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
