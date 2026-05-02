package org.example.logistics_crm.dto.driver.response;

import org.example.logistics_crm.entity.driver.DriverStatus;

import java.time.LocalDate;

public record DriverDetailsResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String licenseNumber,
        String phoneNumber,
        DriverStatus driverStatus,
        LocalDate createdDate,
        LocalDate updatedDate
) {
}
