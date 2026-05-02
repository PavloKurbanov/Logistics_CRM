package org.example.logistics_crm.service.driver.validation.status;

import org.example.logistics_crm.entity.driver.Driver;
import org.example.logistics_crm.entity.driver.DriverStatus;
import org.example.logistics_crm.service.driver.validation.DriverStatusValidator;

public class ToSickLeave implements DriverStatusValidator {

    @Override
    public DriverStatus getDriverStatus() {
        return DriverStatus.SICK_LEAVE;
    }

    @Override
    public void validate(Driver driver) {
        if (driver.getDriverStatus() != DriverStatus.AVAILABLE) {
            throw new IllegalStateException(String.format(
                    "Cannot send driver on sick leave. Current status: %s. Only AVAILABLE drivers can go on sick leave.", driver.getDriverStatus()));
        }
    }
}
