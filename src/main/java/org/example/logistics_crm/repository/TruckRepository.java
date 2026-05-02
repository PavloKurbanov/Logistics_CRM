package org.example.logistics_crm.repository;

import org.example.logistics_crm.entity.truck.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TruckRepository extends JpaRepository<Truck, Long> {
    boolean existsByLicenseNumber(String licenseNumber);
}
