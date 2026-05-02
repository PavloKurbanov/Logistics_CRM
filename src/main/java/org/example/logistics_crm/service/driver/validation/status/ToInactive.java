package org.example.logistics_crm.service.driver.validation.status;

import org.example.logistics_crm.entity.driver.Driver;
import org.example.logistics_crm.entity.driver.DriverStatus;
import org.example.logistics_crm.service.driver.validation.DriverStatusValidator;
import org.springframework.stereotype.Component;

@Component
public class ToInactive implements DriverStatusValidator {

    @Override
    public DriverStatus getDriverStatus() {
        return DriverStatus.INACTIVE;
    }

    @Override
    public void validate(Driver driver) {
        if (driver.getDriverStatus() == DriverStatus.INACTIVE) {
            throw new IllegalStateException("Driver is already INACTIVE.");
        }
    }
}
