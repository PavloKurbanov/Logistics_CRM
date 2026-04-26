package org.example.logistics_crm.order.repository;

import org.example.logistics_crm.order.Order;
import org.example.logistics_crm.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderStatus(OrderStatus status);

    List<Order> findByDeliveryDate(LocalDate date);

    List<Order> findByCreationDate(LocalDate date);

    List<Order> findByPriceBetween(BigDecimal min, BigDecimal max);

    List<Order> findByWeightBetween(Double min, Double max);

    List<Order> findByPickupAddress(String pickupAddress);

    List<Order> findByDeliveryAddress(String deliveryAddress);
}
