package org.example.logistics_crm.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateOrderRequestDTO(
        Long senderClientId,
        Long receiverClientId,
        String pickupAddress,
        String deliveryAddress,
        BigDecimal price,
        Double weight,
        LocalDateTime deliveryDate
) {
}
