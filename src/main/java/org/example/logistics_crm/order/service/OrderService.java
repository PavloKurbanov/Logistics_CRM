package org.example.logistics_crm.order.service;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;
import org.example.logistics_crm.order.dto.request.CreateOrderRequestDTO;
import org.example.logistics_crm.order.dto.request.OrderSearchRequestDTO;
import org.example.logistics_crm.order.dto.response.OrderDetailsResponseDTO;
import org.example.logistics_crm.order.dto.response.OrderListResponseDTO;

import java.util.List;

public interface OrderService {
    OrderDetailsResponseDTO createOrder(CreateOrderRequestDTO request);

    void updateOrder(Order order);

    void deleteOrder(Long orderId);

    OrderDetailsResponseDTO getOrderById(Long id);

    List<OrderListResponseDTO> getAllOrders();

    List<OrderListResponseDTO> searchOrders(OrderSearchRequestDTO request);

    OrderDetailsResponseDTO updateOrderStatus(Long orderId, OrderStatus orderStatus);
}
