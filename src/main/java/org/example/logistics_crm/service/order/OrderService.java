package org.example.logistics_crm.service.order;

import org.example.logistics_crm.dto.order.request.CreateOrderRequestDTO;
import org.example.logistics_crm.dto.order.request.OrderSearchRequestDTO;
import org.example.logistics_crm.dto.order.response.OrderDetailsResponseDTO;
import org.example.logistics_crm.dto.order.response.OrderListResponseDTO;
import org.example.logistics_crm.entity.order.Order;
import org.example.logistics_crm.entity.order.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDetailsResponseDTO createOrder(CreateOrderRequestDTO request);

    void updateOrder(Order order);

    void deleteOrder(Long orderId);

    OrderDetailsResponseDTO getOrderById(Long id);

    Page<OrderListResponseDTO> getAllOrders(Pageable pageable);

    Page<OrderListResponseDTO> searchOrders(OrderSearchRequestDTO request, Pageable pageable);

    OrderDetailsResponseDTO updateOrderStatus(Long orderId, OrderStatus orderStatus);

    Order findOrderEntityById(Long orderId);
}
