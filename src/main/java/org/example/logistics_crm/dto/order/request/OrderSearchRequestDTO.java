package org.example.logistics_crm.dto.order.request;

import org.example.logistics_crm.entity.order.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderSearchRequestDTO(
        String senderFirstName,
        String senderLastName,
        String receiverFirstName,
        String receiverLastName,
        OrderStatus status,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Double minWeight,
        Double maxWeight,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String pickupAddress,
        String deliveryAddress
) {
}
