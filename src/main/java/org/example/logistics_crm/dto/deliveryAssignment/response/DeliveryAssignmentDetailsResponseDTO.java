package org.example.logistics_crm.dto.deliveryAssignment.response;

import java.time.LocalDateTime;

public record DeliveryAssignmentDetailsResponseDTO(
        Long id,
        Long orderId,
        String orderCode,
        String senderFullName,
        String recipientFullName,
        Long driverId,
        String driverFullName,
        Long truckId,
        String truckLicensePlate,
        String status,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
