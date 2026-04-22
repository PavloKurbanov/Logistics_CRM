package org.example.logistics_crm.order.service;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;
import org.example.logistics_crm.order.dto.CreateOrderRequestDTO;

import java.util.List;

public interface OrderService {
    Order createOrder(CreateOrderRequestDTO request);

    void deleteOrder(Long orderId);

    Order getOrderById(Long id);

    List<Order> getAllOrders();

    Order updateOrderStatus(Long orderId, OrderStatus orderStatus);
}
