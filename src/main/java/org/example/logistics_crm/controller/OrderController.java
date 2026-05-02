package org.example.logistics_crm.controller;

import jakarta.validation.Valid;
import org.example.logistics_crm.dto.order.request.CreateOrderRequestDTO;
import org.example.logistics_crm.dto.order.request.OrderSearchRequestDTO;
import org.example.logistics_crm.dto.order.response.OrderDetailsResponseDTO;
import org.example.logistics_crm.dto.order.response.OrderListResponseDTO;
import org.example.logistics_crm.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDetailsResponseDTO createOrder(
            @RequestBody @Valid CreateOrderRequestDTO request) {
        return orderService.createOrder(request);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderListResponseDTO> getOrders(Pageable pageable) {
        return orderService.getAllOrders(pageable);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderListResponseDTO> searchOrders(
            @ModelAttribute OrderSearchRequestDTO request, Pageable pageable){
        return orderService.searchOrders(request, pageable);
    }
}
