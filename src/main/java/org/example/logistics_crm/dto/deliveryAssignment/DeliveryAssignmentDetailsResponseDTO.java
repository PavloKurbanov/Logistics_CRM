package org.example.logistics_crm.dto.deliveryAssignment;

import java.time.LocalDateTime;

public record DeliveryAssignmentDetailsResponseDTO(
        Long id,
        Long orderId,
        String orderNumber,
        Long driverId,
        String driverFullName,
        Long truckId,
        String truckLicensePlate,
        String status,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
