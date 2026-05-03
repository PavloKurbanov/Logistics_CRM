package org.example.logistics_crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.example.logistics_crm.entity.driver.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long>, JpaSpecificationExecutor<Driver> {
    boolean existByLicenseNumber(String licenseNumber);

    boolean existByPhoneNumber(String number);
}
