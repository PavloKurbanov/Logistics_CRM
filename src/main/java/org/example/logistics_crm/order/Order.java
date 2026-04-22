package org.example.logistics_crm.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.example.logistics_crm.client.Client;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "order_code", unique = true, nullable = false)
    private String orderCode;

    @NotBlank
    @Column(name = "tracking_code", unique = true, nullable = false)
    private String trackingCode;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_client_id", nullable = false)
    private Client senderClient;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "receiver_client_id", nullable = false)
    private Client receiverClient;

    @NotBlank
    @Column(name = "pickup_address", nullable = false)
    private String pickupAddress;

    @NotBlank
    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @NotNull
    @DecimalMin("0")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @NotNull
    @PositiveOrZero
    @Column(name = "weight", nullable = false)
    private Double weight;

    @NotNull
    @Column(name = "delivery_date", nullable = false)
    private LocalDateTime deliveryDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus = OrderStatus.CREATED;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public Order(Client senderClient,
                 Client receiverClient, String pickupAddress, String deliveryAddress,
                 BigDecimal price, Double weight, LocalDateTime deliveryDate) {
        this.senderClient = senderClient;
        this.receiverClient = receiverClient;
        this.pickupAddress = pickupAddress;
        this.deliveryAddress = deliveryAddress;
        this.price = price;
        this.weight = weight;
        this.deliveryDate = deliveryDate;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Client getSenderClient() {
        return senderClient;
    }

    public void setSenderClient(Client senderClient) {
        this.senderClient = senderClient;
    }

    public Client getReceiverClient() {
        return receiverClient;
    }

    public void setReceiverClient(Client receiverClient) {
        this.receiverClient = receiverClient;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @PrePersist
    protected void prePersist() {
        this.creationDate = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Order order = (Order) object;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
