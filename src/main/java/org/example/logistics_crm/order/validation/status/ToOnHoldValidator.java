package org.example.logistics_crm.order.validation.status;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;
import org.example.logistics_crm.order.validation.OrderStatusValidator;
import org.springframework.stereotype.Component;

@Component
public class ToOnHoldValidator implements OrderStatusValidator {

    @Override
    public OrderStatus getOrderStatus() {
        return OrderStatus.ON_HOLD;
    }

    @Override
    public void validate(Order order) {
        if (order.getOrderStatus() != OrderStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot change status to ON_HOLD. Current status: "
                    + order.getOrderStatus());
        }
    }
}
