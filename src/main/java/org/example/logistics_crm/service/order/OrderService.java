package org.example.logistics_crm.service.order;

import org.example.logistics_crm.entity.order.Order;
import org.example.logistics_crm.entity.order.OrderStatus;
import org.example.logistics_crm.dto.order.request.CreateOrderRequestDTO;
import org.example.logistics_crm.dto.order.request.OrderSearchRequestDTO;
import org.example.logistics_crm.dto.order.response.OrderDetailsResponseDTO;
import org.example.logistics_crm.dto.order.response.OrderListResponseDTO;

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
