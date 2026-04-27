package org.example.logistics_crm.client.dto.response;

public record ClientListResponseDTO(
        Long id,
        String firstName,
        String LastName,
        String email,
        String phoneNumber
) {
}
