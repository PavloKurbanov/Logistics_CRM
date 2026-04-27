package org.example.logistics_crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
}
