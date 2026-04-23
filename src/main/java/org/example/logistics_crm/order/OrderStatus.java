package org.example.logistics_crm.order;

public enum OrderStatus {
    CREATED,
    CONFIRMED,
    ASSIGNED,
    PICKED_UP,
    IN_TRANSIT,
    DELIVERED,
    CANCELLED,
    ON_HOLD,
    DELAYED,
    UNSUPPORTED
}

/*
CREATED
Замовлення щойно створене, ще ніхто не перевіряв.

CONFIRMED
Менеджер підтвердив, що доставка можлива.

ASSIGNED
Призначено водія + транспорт.

PICKED_UP
Вантаж вже забраний у відправника.

IN_TRANSIT
Посилка в дорозі.

DELIVERED
Успішно доставлено.

CANCELLED
Замовлення скасовано. (важливо: це terminal state)

ON_HOLD
Тимчасова зупинка: клієнт не відповідає проблема з  документами немає доступу до складу

DELAYED
Затримка через форс-мажор:
ДТП поломка
погодні умови
проблеми на
митниці*/
