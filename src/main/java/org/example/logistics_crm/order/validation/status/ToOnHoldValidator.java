package org.example.logistics_crm.order.validation.status;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;

public class ToOnHoldValidator implements OrderStatusValidator {

    @Override
    public boolean supports(OrderStatus newStatus) {
        return newStatus == OrderStatus.ON_HOLD;
    }

    @Override
    public void validate(Order order) {
        if (order.getOrderStatus() != OrderStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot change status to ON_HOLD. Current status: "
                    + order.getOrderStatus());
        }
    }
}
