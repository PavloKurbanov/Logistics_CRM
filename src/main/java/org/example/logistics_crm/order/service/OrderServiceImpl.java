package org.example.logistics_crm.order.service;

import jakarta.transaction.Transactional;
import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;
import org.example.logistics_crm.order.dto.CreateOrderRequestDTO;
import org.example.logistics_crm.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public Order createOrder(CreateOrderRequestDTO request) {

        return null;
    }

    @Override
    public void deleteOrder(Long orderId) {

    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Order> getAllOrders() {
        return List.of();
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        return null;
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return List.of();
    }

    @Override
    public List<Order> getOrdersByDeliveryDate(LocalDate date) {
        return List.of();
    }

    @Override
    public List<Order> getOrdersByCreationDate(LocalDate date) {
        return List.of();
    }

    @Override
    public List<Order> getOrdersByPriceBetween(BigDecimal min, BigDecimal max) {
        return List.of();
    }

    @Override
    public List<Order> getOrdersByWeightBetween(Double min, Double max) {
        return List.of();
    }

    @Override
    public List<Order> getOrdersByPickupAddress(String pickupAddress) {
        return List.of();
    }

    @Override
    public List<Order> getOrdersByDeliveryAddress(String deliveryAddress) {
        return List.of();
    }
}
