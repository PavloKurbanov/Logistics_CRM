package org.example.logistics_crm.dto.driver.request;

import jakarta.validation.constraints.NotBlank;

public record CreateDriverRequestDTO(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String licenseNumber,
        @NotBlank
        String phoneNumber
) {
}
