package org.example.logistics_crm.dto.deliveryAssignment.request;

import java.time.LocalDateTime;

public record DeliveryAssignmentSearchRequestDTO(
        // 1. Точні ID (для прямих переходів з інших сторінок)
        Long orderId,
        Long driverId,
        Long truckId,

        // 2. Текстові фільтри (для пошуку "на льоту" через LIKE)
        String orderCode,
        String driverFullName,
        String truckLicensePlate,

        // 3. Контрагенти (те, про що ти запитав — СУПЕР ВАЖЛИВО)
        String senderFirstName,      // Пошук за назвою відправника
        String senderLastName,      // Пошук за назвою відправника
        String recipientFirstName,   // Пошук за назвою отримувача
        String recipientLastName,   // Пошук за назвою отримувача

        // 4. Статус рейсу
        String status,

        // 5. Часові межі
        LocalDateTime createdDateFrom,
        LocalDateTime createdDateTo
) {
}