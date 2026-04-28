package org.example.logistics_crm.dto.order.response;

import org.example.logistics_crm.entity.order.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderListResponseDTO(
        Long id,
        String orderCode,
        String senderClientFullName,
        String receiverClientFullName,
        OrderStatus orderStatus,
        BigDecimal price,
        LocalDateTime deliveryDate
) {
}
