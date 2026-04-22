package org.example.logistics_crm.order.service;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;
import org.example.logistics_crm.order.dto.CreateOrderRequestDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(CreateOrderRequestDTO request);

    void deleteOrder(Long orderId);

    Optional<Order> getOrderById(Long id);

    List<Order> getAllOrders();

    Order updateOrderStatus(Long orderId, OrderStatus orderStatus);

    List<Order> getOrdersByStatus(OrderStatus status);

    List<Order> getOrdersByDeliveryDate(LocalDate date);

    List<Order> getOrdersByCreationDate(LocalDate date);

    List<Order> getOrdersByPriceBetween(BigDecimal min, BigDecimal max);

    List<Order> getOrdersByWeightBetween(Double min, Double max);

    List<Order> getOrdersByPickupAddress(String pickupAddress);

    List<Order> getOrdersByDeliveryAddress(String deliveryAddress);
}
