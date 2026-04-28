package org.example.logistics_crm.service.order.validation.status;

import org.example.logistics_crm.entity.order.Order;
import org.example.logistics_crm.entity.order.OrderStatus;
import org.example.logistics_crm.service.order.validation.OrderStatusValidator;
import org.springframework.stereotype.Component;

@Component
public class UnsupportedOperationValidator implements OrderStatusValidator {

    @Override
    public OrderStatus getOrderStatus() {
        return OrderStatus.UNSUPPORTED;
    }

    @Override
    public void validate(Order order) {
        throw new UnsupportedOperationException("Not supported yet." + order.getOrderStatus());
    }
}
