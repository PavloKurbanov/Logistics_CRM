package org.example.logistics_crm.order.validation.status;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;
import org.example.logistics_crm.order.validation.OrderStatusValidator;
import org.springframework.stereotype.Component;

@Component
public class ToDeliveredValidator implements OrderStatusValidator {

    @Override
    public OrderStatus getOrderStatus() {
        return OrderStatus.DELIVERED;
    }

    @Override
    public void validate(Order order) {
        if (order.getOrderStatus() != OrderStatus.IN_TRANSIT) {
            throw new IllegalArgumentException("Cannot change status to DELIVERED. Current status: "
                    + order.getOrderStatus());
        }
    }
}
