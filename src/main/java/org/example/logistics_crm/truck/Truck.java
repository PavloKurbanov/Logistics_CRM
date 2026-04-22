package org.example.logistics_crm.truck;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotBlank
    @Column(unique = true)
    private String licenseNumber;

    @NotNull
    @Min(1)
    private Double capacity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TruckStatus truckStatus = TruckStatus.AVAILABLE;

    public Truck(String brand, String model, String licenseNumber, Double capacity) {
        this.brand = brand;
        this.model = model;
        this.licenseNumber = licenseNumber;
        this.capacity = capacity;
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
}
