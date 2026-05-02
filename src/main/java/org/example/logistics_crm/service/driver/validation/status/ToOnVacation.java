package org.example.logistics_crm.service.driver.validation.status;

import org.example.logistics_crm.entity.driver.Driver;
import org.example.logistics_crm.entity.driver.DriverStatus;
import org.example.logistics_crm.service.driver.validation.DriverStatusValidator;

public class ToOnVacation implements DriverStatusValidator {

    @Override
    public DriverStatus getDriverStatus() {
        return DriverStatus.ON_VACATION;
    }

    @Override
    public void validate(Driver driver) {
        if (driver.getDriverStatus() != DriverStatus.AVAILABLE) {
            throw new IllegalStateException(String.format(
                    "Cannot send driver on vacation. Current status: %s. Only AVAILABLE drivers can go on vacation.", driver.getDriverStatus()));
        }
    }
}
