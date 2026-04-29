package org.example.logistics_crm.dto.client.response;

public record ClientListResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
}
