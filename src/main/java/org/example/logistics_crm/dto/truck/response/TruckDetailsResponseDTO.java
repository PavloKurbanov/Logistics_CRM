package org.example.logistics_crm.dto.truck.response;

import org.example.logistics_crm.entity.truck.TruckStatus;

import java.time.LocalDateTime;

public record TruckDetailsResponseDTO(
        Long id,
        String brand,
        String model,
        String licenseNumber,
        Double capacity,
        TruckStatus truckStatus,
        LocalDateTime createDate,
        LocalDateTime updateDate
) {
}
