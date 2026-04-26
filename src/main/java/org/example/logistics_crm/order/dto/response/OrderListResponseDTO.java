package org.example.logistics_crm.order.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.logistics_crm.order.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderListResponseDTO(
        @NotNull
        Long id,
        @NotBlank
        String orderCode,
        @NotBlank
        String senderClientFullName,
        @NotBlank
        String receiverClientFullName,
        @NotNull
        OrderStatus orderStatus,
        @NotNull
        BigDecimal price,
        @NotNull
        LocalDateTime deliveryDate
) {
}
