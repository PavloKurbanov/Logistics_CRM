package org.example.logistics_crm.order.dto.response;

import org.example.logistics_crm.order.OrderStatus;

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
