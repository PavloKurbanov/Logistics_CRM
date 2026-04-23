package org.example.logistics_crm.order.validation.status;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;
import org.example.logistics_crm.order.validation.OrderStatusValidator;
import org.springframework.stereotype.Component;

@Component
public class ToInTransitValidator implements OrderStatusValidator {

    @Override
    public OrderStatus getOrderStatus() {
        return OrderStatus.IN_TRANSIT;
    }

    @Override
    public void validate(Order order) {
        if (order.getOrderStatus() != OrderStatus.PICKED_UP) {
            throw new IllegalArgumentException("Cannot change status to IN_TRANSIT. Current status: "
                    + order.getOrderStatus());
        }
    }
}
