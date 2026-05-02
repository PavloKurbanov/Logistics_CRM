package org.example.logistics_crm.service.driver.validation.status;

import org.example.logistics_crm.entity.driver.Driver;
import org.example.logistics_crm.entity.driver.DriverStatus;

public class ToInactive extends ToOnVacation {

    @Override
    public DriverStatus getDriverStatus() {
        return DriverStatus.INACTIVE;
    }

     @Override
    public void validate(Driver driver) {
        if (driver.getDriverStatus() != DriverStatus.AVAILABLE) {
            throw new IllegalStateException(String.format(
                    "Cannot send driver on inactive. Current status: %s. Only AVAILABLE drivers can go on inactive.", driver.getDriverStatus()));
        }
    }
}
