package org.example.logistics_crm.dto.truck.response;

import org.example.logistics_crm.entity.truck.TruckStatus;
import org.example.logistics_crm.service.truck.TruckService;

public record TruckListResponseDTO(
        Long id,
        String brand,
        String model,
        String licenseNumber,
        Double capacity,
        TruckStatus truckStatus
) {
}
