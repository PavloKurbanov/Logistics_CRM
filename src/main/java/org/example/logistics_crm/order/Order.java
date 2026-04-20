package org.example.logistics_crm.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.logistics_crm.client.Client;
import org.example.logistics_crm.driver.Driver;
import org.example.logistics_crm.truck.Truck;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String orderCode;

    @NotNull
    @NotBlank
    private String trackingCode;

    @NotNull
    private LocalDateTime updatedAt;

    @NotNull
    @ManyToOne(optional = false)
    private Client senderClient;

    @NotNull
    @ManyToOne(optional = false)
    private Client receiverClient;

    @NotNull
    @NotBlank
    private String pickupAddress;

    @NotNull
    @NotBlank
    private String deliveryAddress;

    @NotNull
    @DecimalMin("0")
    private BigDecimal price;

    @NotNull
    @Min(0)
    private Double weight;

    @NotNull
    private LocalDateTime creationDate;

    @NotNull
    private LocalDateTime deliveryDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    private Driver driver;

    @ManyToOne
    private Truck truck;

    public Order(String orderCode, String trackingCode, Client senderClient,
                 Client receiverClient, String pickupAddress, String deliveryAddress,
                 BigDecimal price, Double weight, LocalDateTime deliveryDate) {
        this.orderCode = orderCode;
        this.trackingCode = trackingCode;
        this.senderClient = senderClient;
        this.receiverClient = receiverClient;
        this.pickupAddress = pickupAddress;
        this.deliveryAddress = deliveryAddress;
        this.price = price;
        this.weight = weight;
        this.deliveryDate = deliveryDate;
        this.creationDate = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.orderStatus = OrderStatus.CREATED;
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

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }
}
