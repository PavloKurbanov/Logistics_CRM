package org.example.logistics_crm.client.dto;

public record CreateClientRequestDTO(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String password
) {
}
