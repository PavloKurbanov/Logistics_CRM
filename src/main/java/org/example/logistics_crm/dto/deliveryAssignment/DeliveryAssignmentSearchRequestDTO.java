package org.example.logistics_crm.dto.deliveryAssignment;

import java.time.LocalDate;

public record DeliveryAssignmentSearchRequestDTO(
        Long driverId,
        Long truckId,
        Long orderId,
        String status,
        LocalDate dateFrom,
        LocalDate dateTo
) {
}
