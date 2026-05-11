package org.example.logistics_crm.dto.deliveryAssignment.request;

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
