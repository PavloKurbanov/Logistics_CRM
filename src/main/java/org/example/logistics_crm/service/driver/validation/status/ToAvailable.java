package org.example.logistics_crm.service.driver.validation.status;

import org.example.logistics_crm.entity.driver.Driver;
import org.example.logistics_crm.entity.driver.DriverStatus;
import org.example.logistics_crm.service.driver.validation.DriverStatusValidator;

public class ToAvailable implements DriverStatusValidator {

    @Override
    public DriverStatus getDriverStatus() {
        return DriverStatus.AVAILABLE;
    }

    @Override
    public void validate(Driver driver) {
        if (driver.getDriverStatus() != DriverStatus.ON_VACATION
                && driver.getDriverStatus() != DriverStatus.SICK_LEAVE
                && driver.getDriverStatus() != DriverStatus.ON_DELIVERY) {
            throw new IllegalStateException(String.format(
                    "Cannot transition driver to AVAILABLE. Current status '%s' does not allow this change.",
                    driver.getDriverStatus()));
        }
    }
}
