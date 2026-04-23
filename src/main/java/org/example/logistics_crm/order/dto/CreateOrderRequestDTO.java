package org.example.logistics_crm.order.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateOrderRequestDTO(
        @NotBlank
        Long senderClientId,
        @NotNull
        Long receiverClientId,
        @NotBlank
        String pickupAddress,
        @NotBlank
        String deliveryAddress,
        @NotNull
        @DecimalMin("0")
        BigDecimal price,
        @NotNull
        @Min(1)
        Double weight,
        @NotNull
        LocalDateTime deliveryDate
) {
}
