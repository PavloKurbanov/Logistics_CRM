package org.example.logistics_crm.service.order.validation;

import org.example.logistics_crm.entity.order.Order;
import org.example.logistics_crm.entity.order.OrderStatus;

public interface OrderStatusValidator {

    OrderStatus getOrderStatus();

    void validate(Order order);
}
