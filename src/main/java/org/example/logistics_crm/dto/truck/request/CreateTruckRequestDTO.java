package org.example.logistics_crm.dto.truck.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateTruckRequestDTO(
        @NotBlank
        String brand,
        @NotBlank
        String model,
        @NotBlank
        String licenseNumber,
        @NotNull
        @Positive
        Double capacity
) {
}
