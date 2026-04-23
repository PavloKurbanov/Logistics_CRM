package org.example.logistics_crm.order.validation.status;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;
import org.example.logistics_crm.order.validation.OrderStatusValidator;
import org.springframework.stereotype.Component;

@Component
public class ToCancelledValidator implements OrderStatusValidator {

    @Override
    public OrderStatus getOrderStatus() {
        return OrderStatus.CANCELLED;
    }

    @Override
    public void validate(Order order) {
        if (order.getOrderStatus() != OrderStatus.DELIVERED) {
            throw new IllegalArgumentException("Cannot change status to CANCELLED. Current status: "
                    + order.getOrderStatus());
        }
    }
}
