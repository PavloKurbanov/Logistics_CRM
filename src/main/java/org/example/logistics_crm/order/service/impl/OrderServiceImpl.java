package org.example.logistics_crm.order.service.impl;

import jakarta.transaction.Transactional;
import org.example.logistics_crm.client.Client;
import org.example.logistics_crm.client.service.ClientService;
import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;
import org.example.logistics_crm.order.dto.CreateOrderRequestDTO;
import org.example.logistics_crm.order.repository.OrderRepository;
import org.example.logistics_crm.order.service.OrderService;
import org.example.logistics_crm.order.validation.status.OrderStatusValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final List<OrderStatusValidator> validators;
    private final ClientService clientService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, List<OrderStatusValidator> validators, ClientService clientService) {
        this.orderRepository = orderRepository;
        this.validators = validators;
        this.clientService = clientService;
    }

    @Override
    @Transactional
    public Order createOrder(CreateOrderRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("Помилка створення замовлення");
        }

        Client senderClient = clientService.findById(request.senderClientId())
                .orElseThrow(() -> new IllegalArgumentException("Sender client id not found"));

        Client receiverClient = clientService.findById(request.receiverClientId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver client id not found"));

        if (senderClient.equals(receiverClient)) {
            throw new IllegalArgumentException("Sender client and receiver client are the same");
        }

        return orderRepository.save(
                new Order(
                        senderClient,
                        receiverClient,
                        request.pickupAddress(),
                        request.deliveryAddress(),
                        request.price(),
                        request.weight(),
                        request.deliveryDate()
                ));
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Введіть коректне ID замовлення");
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Введіть коректне ID замовлення");
        }
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID can't ve null");
        }
        if (orderStatus == null) {
            throw new IllegalArgumentException("Order status can't be null");
        }
        Order order = getOrderById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (order.getOrderStatus() == orderStatus) {
            throw new IllegalStateException(
                    "Order already has this status"
            );
        }

        validators.stream()
                .filter(v -> v.supports(orderStatus))
                .forEach(v -> v.validate(order));

        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Order status can't be null");
        }
        return orderRepository.findByStatus(status);
    }

    @Override
    public List<Order> getOrdersByDeliveryDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Order delivery date can't be null");
        }
        return orderRepository.findByDeliveryDate(date);
    }

    @Override
    public List<Order> getOrdersByCreationDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Order creation date can't be null");
        }
        return orderRepository.findByCreationDate(date);
    }

    @Override
    public List<Order> getOrdersByPriceBetween(BigDecimal min, BigDecimal max) {
        if (min == null || max == null) {
            throw new IllegalArgumentException("Order price can't be null");
        }
        return orderRepository.findByPriceBetween(min, max);
    }

    @Override
    public List<Order> getOrdersByWeightBetween(Double min, Double max) {
        if (min == null || max == null) {
            throw new IllegalArgumentException("Order weight can't be null");
        }
        return orderRepository.findByWeightBetween(min, max);
    }

    @Override
    public List<Order> getOrdersByPickupAddress(String pickupAddress) {
        if (pickupAddress == null) {
            throw new IllegalArgumentException("Order pickup address can't be null");
        }
        return orderRepository.findByPickupAddress(pickupAddress);
    }

    @Override
    public List<Order> getOrdersByDeliveryAddress(String deliveryAddress) {
        if (deliveryAddress == null) {
            throw new IllegalArgumentException("Order delivery address can't be null");
        }
        return orderRepository.findByDeliveryAddress(deliveryAddress);
    }
}
