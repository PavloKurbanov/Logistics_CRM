package org.example.logistics_crm.order.validation.status;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;
import org.example.logistics_crm.order.validation.OrderStatusValidator;
import org.springframework.stereotype.Component;

@Component
public class ToDelayedValidator implements OrderStatusValidator {

    @Override
    public OrderStatus getOrderStatus() {
        return OrderStatus.DELAYED;
    }

    @Override
    public void validate(Order order) {
        if (order.getOrderStatus() != OrderStatus.ON_HOLD) {
            throw new IllegalArgumentException("Cannot change status to DELAYED. Current status: "
                    + order.getOrderStatus());
        }
    }
}
