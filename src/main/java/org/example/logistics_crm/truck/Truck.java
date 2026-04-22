package org.example.logistics_crm.truck;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "trucks")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "brand", nullable = false)
    private String brand;

    @NotBlank
    @Column(name = "model", nullable = false)
    private String model;

    @NotBlank
    @Column(name = "license_number", unique = true, nullable = false)
    private String licenseNumber;

    @NotNull
    @Min(1)
    @Column(name = "capacity", nullable = false)
    private Double capacity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "truck_status", nullable = false)
    private TruckStatus truckStatus = TruckStatus.AVAILABLE;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createdDate;

    @NotNull
    @Column(name = "update_date", nullable = false)
    private LocalDateTime updatedDate;

    public Truck(String brand, String model, String licenseNumber, Double capacity) {
        this.brand = brand;
        this.model = model;
        this.licenseNumber = licenseNumber;
        this.capacity = capacity;
    }

    public Truck() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public TruckStatus getTruckStatus() {
        return truckStatus;
    }

    public void setTruckStatus(TruckStatus truckStatus) {
        this.truckStatus = truckStatus;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @PrePersist
    protected void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Truck truck = (Truck) object;
        return Objects.equals(id, truck.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
