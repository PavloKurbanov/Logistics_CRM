package org.example.logistics_crm.dto.truck.request;

import org.example.logistics_crm.entity.truck.TruckStatus;

import java.time.LocalDateTime;

public record SearchTruckRequestDTO(
        String model,
        String brand,
        String licenseNumber,
        Double capacityFrom,
        Double capacityTo,
        TruckStatus status,
        LocalDateTime createdFrom,
        LocalDateTime createdTo
) {
}
