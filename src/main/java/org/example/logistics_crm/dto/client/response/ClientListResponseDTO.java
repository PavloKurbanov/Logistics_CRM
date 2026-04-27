package org.example.logistics_crm.dto.client.response;

public record ClientListResponseDTO(
        Long id,
        String firstName,
        String LastName,
        String email,
        String phoneNumber
) {
}
