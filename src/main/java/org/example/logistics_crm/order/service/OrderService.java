package org.example.logistics_crm.order.service;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;
import org.example.logistics_crm.order.dto.request.CreateOrderRequestDTO;
import org.example.logistics_crm.order.dto.response.OrderDetailsResponseDTO;
import org.example.logistics_crm.order.dto.response.OrderListResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    OrderDetailsResponseDTO createOrder(CreateOrderRequestDTO request);

    void updateOrder(Order order);

    void deleteOrder(Long orderId);

    OrderDetailsResponseDTO getOrderById(Long id);

    List<OrderListResponseDTO> getAllOrders();

    OrderDetailsResponseDTO updateOrderStatus(Long orderId, OrderStatus orderStatus);

    List<OrderListResponseDTO> getOrdersByStatus(OrderStatus status);

    List<OrderListResponseDTO> getOrdersByDeliveryDate(LocalDate date);

    List<OrderListResponseDTO> getOrdersByCreationDate(LocalDate date);

    List<OrderListResponseDTO> getOrdersByPriceBetween(BigDecimal min, BigDecimal max);

    List<OrderListResponseDTO> getOrdersByWeightBetween(Double min, Double max);

    List<OrderListResponseDTO> getOrdersByPickupAddress(String pickupAddress);

    List<OrderListResponseDTO> getOrdersByDeliveryAddress(String deliveryAddress);
}
