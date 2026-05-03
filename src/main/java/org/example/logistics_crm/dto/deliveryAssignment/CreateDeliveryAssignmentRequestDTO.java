package org.example.logistics_crm.dto.deliveryAssignment;

import jakarta.validation.constraints.NotNull;

public record CreateDeliveryAssignmentRequestDTO(
        @NotNull
        Long orderId,
        @NotNull
        Long driverId,
        @NotNull
        Long truckId
) {
}
