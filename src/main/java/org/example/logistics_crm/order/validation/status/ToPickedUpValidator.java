package org.example.logistics_crm.order.validation.status;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;
import org.example.logistics_crm.order.validation.OrderStatusValidator;
import org.springframework.stereotype.Component;

@Component
public class ToPickedUpValidator implements OrderStatusValidator {

    @Override
    public OrderStatus getOrderStatus() {
        return OrderStatus.PICKED_UP;
    }

    @Override
    public void validate(Order order) {
        if (order.getOrderStatus() != OrderStatus.ASSIGNED) {
            throw new IllegalArgumentException("Cannot change status to PIKED_UP. Current status: "
                    + order.getOrderStatus());
        }
    }
}
