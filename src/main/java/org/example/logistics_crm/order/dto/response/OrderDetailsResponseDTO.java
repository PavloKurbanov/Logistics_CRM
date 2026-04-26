package org.example.logistics_crm.order.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.logistics_crm.order.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDetailsResponseDTO(
        @NotNull
        Long id,
        @NotBlank
        String orderCode,
        @NotBlank
        String trackingCode,

        @NotNull
        Long senderClientId,
        @NotBlank
        String senderClientFullName,

        @NotNull
        Long receiverClientId,
        @NotBlank
        String receiverClientFullName,

        @NotBlank
        String pickupAddress,
        @NotBlank
        String deliveryAddress,

        @NotNull
        BigDecimal price,
        @NotNull
        Double weight,

        @NotNull
        LocalDateTime deliveryDate,
        @NotNull
        OrderStatus orderStatus,

        @NotNull
        LocalDateTime creationDate,
        @NotNull
        LocalDateTime updatedAt
) {
}
