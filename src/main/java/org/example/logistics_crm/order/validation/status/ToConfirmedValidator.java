package org.example.logistics_crm.order.validation.status;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;
import org.example.logistics_crm.order.validation.OrderStatusValidator;
import org.springframework.stereotype.Component;

@Component
public class ToConfirmedValidator implements OrderStatusValidator {

    @Override
    public OrderStatus getOrderStatus() {
        return OrderStatus.CONFIRMED;
    }

    @Override
    public void validate(Order order) {
        if (order.getOrderStatus() != OrderStatus.CREATED) {
            throw new IllegalArgumentException("Cannot change status to CONFIRMED. Current status: "
                    + order.getOrderStatus());
        }
    }
}
