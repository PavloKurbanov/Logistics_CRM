package org.example.logistics_crm.order.validation;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;

public interface OrderStatusValidator {

    OrderStatus getOrderStatus();
    void validate(Order order);
}
