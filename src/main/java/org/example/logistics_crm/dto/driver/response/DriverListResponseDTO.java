package org.example.logistics_crm.dto.driver.response;

import org.example.logistics_crm.entity.driver.DriverStatus;

public record DriverListResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String licenseNumber,
        String phoneNumber,
        DriverStatus driverStatus
) {
}
