package org.example.logistics_crm.order.validation.status;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;

public interface OrderStatusValidator {
    boolean supports(OrderStatus newStatus);

    void validate(Order order);
}
