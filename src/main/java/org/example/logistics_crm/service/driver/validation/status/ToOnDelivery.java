package org.example.logistics_crm.service.driver.validation.status;

import org.example.logistics_crm.entity.driver.DriverStatus;
import org.example.logistics_crm.entity.driver.Driver;
import org.example.logistics_crm.service.driver.validation.DriverStatusValidator;

public class ToOnDelivery implements DriverStatusValidator {

    @Override
    public DriverStatus getDriverStatus() {
        return DriverStatus.ON_DELIVERY;
    }

    @Override
    public void validate(Driver driver) {
        if (driver.getDriverStatus() != DriverStatus.AVAILABLE) {
            throw new IllegalStateException(String.format(
                    "Driver is not ready for delivery. Current status: %s. Must be AVAILABLE.", driver.getDriverStatus()));
        }
    }
}
