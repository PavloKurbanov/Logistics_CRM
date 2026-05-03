package org.example.logistics_crm.specification;

import org.example.logistics_crm.entity.driver.Driver;
import org.example.logistics_crm.entity.driver.DriverStatus;
import org.example.logistics_crm.service.driver.validation.DriverStatusValidator;

public class UnsupportedOperationDriverStatusValidator implements DriverStatusValidator {

    @Override
    public DriverStatus getDriverStatus() {
        return DriverStatus.UNSUPPORTED;
    }

    @Override
    public void validate(Driver driver) {
        throw new UnsupportedOperationException("Not supported yet." + driver.getDriverStatus());
    }
}
