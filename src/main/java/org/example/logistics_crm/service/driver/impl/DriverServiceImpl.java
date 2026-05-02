package org.example.logistics_crm.service.driver.impl;

import org.example.logistics_crm.entity.driver.DriverStatus;
import org.example.logistics_crm.repository.DriverRepository;
import org.example.logistics_crm.service.driver.DriverService;
import org.example.logistics_crm.service.driver.validation.DriverStatusValidator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final Map<DriverStatus, DriverStatusValidator> driverStatusValidators;

    public DriverServiceImpl(DriverRepository driverRepository, List<DriverStatusValidator> validators) {
        this.driverRepository = driverRepository;
        this.driverStatusValidators = validators.stream().collect(Collectors.toMap(DriverStatusValidator::getDriverStatus, validator -> validator));
    }
}
