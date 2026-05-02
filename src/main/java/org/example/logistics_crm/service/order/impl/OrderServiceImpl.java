package org.example.logistics_crm.service.order.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.example.logistics_crm.dto.order.request.CreateOrderRequestDTO;
import org.example.logistics_crm.dto.order.request.OrderSearchRequestDTO;
import org.example.logistics_crm.dto.order.response.OrderDetailsResponseDTO;
import org.example.logistics_crm.dto.order.response.OrderListResponseDTO;
import org.example.logistics_crm.entity.client.Client;
import org.example.logistics_crm.entity.order.Order;
import org.example.logistics_crm.entity.order.OrderStatus;
import org.example.logistics_crm.repository.OrderRepository;
import org.example.logistics_crm.service.client.ClientService;
import org.example.logistics_crm.service.order.OrderService;
import org.example.logistics_crm.service.order.validation.OrderStatusValidator;
import org.example.logistics_crm.specification.OrderSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ClientService clientService;
    private final Map<OrderStatus, OrderStatusValidator> orderStatusValidators;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, List<OrderStatusValidator> validators, ClientService clientService) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.orderStatusValidators = validators.stream().collect(Collectors.toMap(OrderStatusValidator::getOrderStatus, validator -> validator));
    }

    @Override
    @Transactional
    public OrderDetailsResponseDTO createOrder(CreateOrderRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("Order request can't be null");
        }

        log.debug("Attempting to create new order from client with id: {} to client with id: {}",
                request.senderClientId(), request.receiverClientId());

        Client senderClient = clientService.getClientEntityById(request.senderClientId());

        Client receiverClient = clientService.getClientEntityById(request.receiverClientId());

        if (senderClient.equals(receiverClient)) {
            throw new IllegalArgumentException("Sender and receiver clients must be different");
        }

        Order order = new Order(
                senderClient,
                receiverClient,
                request.pickupAddress(),
                request.deliveryAddress(),
                request.price(),
                request.weight(),
                request.deliveryDate()
        );

        order.setOrderCode(generateOrderCode());
        order.setTrackingCode(generateTrackingCode());

        Order savedOrder = orderRepository.save(order);
        log.info("Order with id: {} successfully created from client with id: {} to client with id: {}",
                savedOrder.getId(), request.senderClientId(), request.receiverClientId());

        return mapToDetailsResponseDTO(savedOrder);
    }

    @Override
    public void updateOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order can't be null");
        }
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        if (orderId == null || orderId <= 0L) {
            throw new IllegalArgumentException("Order id must be greater than 0");
        }

        log.debug("Fetching order with id: {} for deletion", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        if (order.getOrderStatus() != OrderStatus.CANCELLED && order.getOrderStatus() != OrderStatus.CREATED) {
            throw new IllegalArgumentException("Order status must be CREATED or CANCELLED");
        }

        orderRepository.delete(order);
        log.info("Order with id: {} successfully deleted", orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetailsResponseDTO getOrderById(Long id) {
        if (id == null || id <= 0L) {
            throw new IllegalArgumentException("Order id must be greater than 0");
        }

        log.debug("Fetching order with id: {} for getting order", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Order not found with id: " + id));

        return mapToDetailsResponseDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderListResponseDTO> getAllOrders(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide pagination parameters.");
        }
        log.debug("Fetching all orders for page: {}", pageable);
        return mapToPageResponseDTO(orderRepository.findAll(pageable));
    }

    @Override
    @Transactional
    public OrderDetailsResponseDTO updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID can't be null");
        }
        if (orderStatus == null) {
            throw new IllegalArgumentException("Order status can't be null");
        }
        log.debug("Attempting to update order status for order with id: {} to status: {}", orderId, orderStatus);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Order not found with id: " + orderId));

        if (order.getOrderStatus() == orderStatus) {
            throw new IllegalStateException(
                    "Order already has this status"
            );
        }

        orderStatusValidators.getOrDefault(orderStatus, orderStatusValidators.get(OrderStatus.UNSUPPORTED)).validate(order);

        order.setOrderStatus(orderStatus);
        log.info("Order with ID: {} successfully updated to status: {}", orderId, orderStatus);
        return mapToDetailsResponseDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderListResponseDTO> searchOrders(OrderSearchRequestDTO request, Pageable pageable) {
        if (request == null) {
            throw new IllegalArgumentException("Search request can't be null");
        }

        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide pagination parameters.");
        }

        log.debug("Searching for orders with criteria: {}", request);
        return mapToPageResponseDTO(orderRepository.findAll(OrderSpecification.search(request), pageable));
    }

    private Page<OrderListResponseDTO> mapToPageResponseDTO(Page<Order> orders) {
        return orders
                .map(order -> new OrderListResponseDTO(
                        order.getId(),
                        order.getOrderCode(),
                        order.getSenderClient().getFirstName() + " " + order.getSenderClient().getLastName(),
                        order.getReceiverClient().getFirstName() + " " + order.getReceiverClient().getLastName(),
                        order.getOrderStatus(),
                        order.getPrice(),
                        order.getDeliveryDate()
                ));
    }

    private OrderDetailsResponseDTO mapToDetailsResponseDTO(Order order) {
        return new OrderDetailsResponseDTO(
                order.getId(),
                order.getOrderCode(),
                order.getTrackingCode(),
                order.getSenderClient().getId(),
                order.getSenderClient().getFirstName() + " " + order.getSenderClient().getLastName(),
                order.getReceiverClient().getId(),
                order.getReceiverClient().getFirstName() + " " + order.getReceiverClient().getLastName(),
                order.getPickupAddress(),
                order.getDeliveryAddress(),
                order.getPrice(),
                order.getWeight(),
                order.getDeliveryDate(),
                order.getOrderStatus(),
                order.getCreationDate(),
                order.getUpdatedAt()
        );
    }

    private String generateOrderCode() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String generateTrackingCode() {
        return "TRK-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }
}
