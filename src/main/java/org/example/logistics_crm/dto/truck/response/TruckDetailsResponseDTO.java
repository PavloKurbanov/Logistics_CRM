package org.example.logistics_crm.dto.truck.response;

import java.time.LocalDateTime;

public record TruckDetailsResponseDTO(
        Long id,
        String brand,
        String model,
        String licenseNumber,
        Double capacity,
        LocalDateTime createDate,
        LocalDateTime updateDate
) {
}
