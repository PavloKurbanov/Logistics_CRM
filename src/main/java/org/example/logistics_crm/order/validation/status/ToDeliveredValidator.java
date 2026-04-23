package org.example.logistics_crm.order.validation.status;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;

public class ToDeliveredValidator implements OrderStatusValidator {

    @Override
    public boolean supports(OrderStatus newStatus) {
        return newStatus == OrderStatus.DELIVERED;
    }

    @Override
    public void validate(Order order) {
        if (order.getOrderStatus() != OrderStatus.IN_TRANSIT) {
            throw new IllegalArgumentException("Cannot change status to DELIVERED. Current status: "
                    + order.getOrderStatus());
        }
    }
}
