package org.example.logistics_crm.order.validation.status;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;

public class ToDelayedValidator implements OrderStatusValidator{

    @Override
    public boolean supports(OrderStatus newStatus) {
        return newStatus == OrderStatus.DELAYED;
    }

    @Override
    public void validate(Order order) {
        if(order.getOrderStatus() != OrderStatus.ON_HOLD) {
            throw new IllegalArgumentException("Cannot change status to DELAYED. Current status: "
                    + order.getOrderStatus());
        }
    }
}
