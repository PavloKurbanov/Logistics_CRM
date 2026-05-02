package org.example.logistics_crm.dto.driver.request;

import org.example.logistics_crm.entity.driver.DriverStatus;

import java.time.LocalDateTime;

public record DriverSearchRequestDTO(
        String firstName,
        String lastName,
        String licenseNumber,
        DriverStatus status,
        String phoneNumber,
        LocalDateTime createdFrom,
        LocalDateTime createdTo
) {
}
