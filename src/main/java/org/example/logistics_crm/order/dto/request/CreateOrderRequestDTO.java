package org.example.logistics_crm.order.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateOrderRequestDTO(
        @NotNull
        @Positive
        Long senderClientId,
        @NotNull
        @Positive
        Long receiverClientId,
        @NotBlank
        String pickupAddress,
        @NotBlank
        String deliveryAddress,
        @NotNull
        @DecimalMin("0")
        BigDecimal price,
        @NotNull
        @Positive
        Double weight,
        @NotNull
        LocalDateTime deliveryDate
) {
}
