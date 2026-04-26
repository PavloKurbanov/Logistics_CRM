package org.example.logistics_crm.order.dto.response;

import org.example.logistics_crm.order.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDetailsResponseDTO(
        Long id,
        String orderCode,
        String trackingCode,

        Long senderClientId,
        String senderClientFullName,

        Long receiverClientId,
        String receiverClientFullName,

        String pickupAddress,
        String deliveryAddress,

        BigDecimal price,
        Double weight,

        LocalDateTime deliveryDate,
        OrderStatus orderStatus,


        LocalDateTime creationDate,
        LocalDateTime updatedAt
) {
}
