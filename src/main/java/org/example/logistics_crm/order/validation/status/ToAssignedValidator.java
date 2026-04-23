package org.example.logistics_crm.order.validation.status;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;
import org.example.logistics_crm.order.validation.OrderStatusValidator;
import org.springframework.stereotype.Component;

@Component
public class ToAssignedValidator implements OrderStatusValidator {

    @Override
    public OrderStatus getOrderStatus() {
        return OrderStatus.ASSIGNED;
    }

    @Override
    public void validate(Order order) {
        if (order.getOrderStatus() != OrderStatus.CONFIRMED) {
            throw new IllegalArgumentException("Cannot change status to ASSIGNED. Current status: "
                    + order.getOrderStatus());
        }
    }
}
