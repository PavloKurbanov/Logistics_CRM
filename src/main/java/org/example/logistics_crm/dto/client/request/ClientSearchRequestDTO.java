package org.example.logistics_crm.dto.client.request;

import java.time.LocalDateTime;

public record ClientSearchRequestDTO(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        LocalDateTime createdFrom,
        LocalDateTime createdTo
) {
}
