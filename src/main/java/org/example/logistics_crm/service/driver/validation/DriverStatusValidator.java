package org.example.logistics_crm.service.driver.validation;

import org.example.logistics_crm.entity.driver.DriverStatus;
import org.example.logistics_crm.entity.driver.Driver;

public interface DriverStatusValidator {

    DriverStatus getDriverStatus();

    void validate(Driver driver);
}
