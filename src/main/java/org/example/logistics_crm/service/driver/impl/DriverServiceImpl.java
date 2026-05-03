package org.example.logistics_crm.service.driver.impl;

import org.example.logistics_crm.entity.driver.Driver;
import org.example.logistics_crm.specification.DriverSpecification;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.logistics_crm.dto.driver.request.CreateDriverRequestDTO;
import org.example.logistics_crm.dto.driver.request.DriverSearchRequestDTO;
import org.example.logistics_crm.dto.driver.response.DriverDetailsResponseDTO;
import org.example.logistics_crm.dto.driver.response.DriverListResponseDTO;
import org.example.logistics_crm.entity.driver.DriverStatus;
import org.example.logistics_crm.repository.DriverRepository;
import org.example.logistics_crm.service.driver.DriverService;
import org.example.logistics_crm.service.driver.validation.DriverStatusValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final Map<DriverStatus, DriverStatusValidator> driverStatusValidators;

    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository, List<DriverStatusValidator> validators) {
        this.driverRepository = driverRepository;
        this.driverStatusValidators = validators.stream().collect(Collectors.toMap(DriverStatusValidator::getDriverStatus, validator -> validator));
    }

    @Override
    @Transactional
    public DriverDetailsResponseDTO createDriver(CreateDriverRequestDTO createDriverRequestDTO) {
        log.debug("Attempt to create a driver with a phoneNumber: {}, licenseNumber: {}", createDriverRequestDTO.phoneNumber(), createDriverRequestDTO.licenseNumber());
        if (createDriverRequestDTO == null) {
            throw new IllegalArgumentException("Request to create a driver cannot be null.");
        }

        if (driverRepository.existByLicenseNumber(createDriverRequestDTO.licenseNumber())) {
            throw new IllegalArgumentException("Driver with license number " + createDriverRequestDTO.licenseNumber() + " already exists.");
        }

        if (driverRepository.existByPhoneNumber(createDriverRequestDTO.phoneNumber())) {
            throw new IllegalArgumentException("Driver with phone number " + createDriverRequestDTO.phoneNumber() + " already exists.");
        }

        Driver driver = driverRepository.save(new Driver(
                createDriverRequestDTO.firstName(),
                createDriverRequestDTO.lastName(),
                createDriverRequestDTO.licenseNumber(),
                createDriverRequestDTO.phoneNumber()
        ));

        log.info("Driver with phone number {}, licenseNumber: {} created successfully with ID: {}"
                , createDriverRequestDTO.phoneNumber(), createDriverRequestDTO.licenseNumber(), driver.getId());
        return mapToDetails(driver);
    }

    @Override
    @Transactional
    public DriverDetailsResponseDTO changeStatus(Long id, DriverStatus driverStatus) {
        log.debug("Attempt to change status of driver with id: {} to status: {}", id, driverStatus);
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Driver id must be greater than 0");
        }

        if (driverStatus == null) {
            throw new IllegalArgumentException("Driver status cannot be null.");
        }

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Driver with id " + id + " not found"));

        if (driver.getDriverStatus() == driverStatus) {
            throw new IllegalArgumentException("Driver with id " + id + " already has status " + driverStatus);
        }

        driverStatusValidators.getOrDefault(driverStatus, driverStatusValidators.get(DriverStatus.UNSUPPORTED)).validate(driver);
        driver.setDriverStatus(driverStatus);
        log.info("Driver with id: {} status changed successfully to status: {}", id, driverStatus);
        return mapToDetails(driver);
    }

    @Override
    @Transactional(readOnly = true)
    public DriverDetailsResponseDTO findById(Long id) {
        log.debug("Fetching driver with ID: {}", id);
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Driver id must be greater than 0");
        }

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Driver with id " + id + " not found"));

        return mapToDetails(driver);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DriverListResponseDTO> findAll(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide pagination parameters.");
        }
        log.debug("Fetching all drivers page request:  {}", pageable);
        return mapToListPage(driverRepository.findAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DriverListResponseDTO> search(DriverSearchRequestDTO request, Pageable pageable) {
        log.debug("Fetching all drivers with criteria:  {}", request);
        if (request == null) {
            throw new IllegalArgumentException("Driver request cannot be null.");
        }

        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide a request.");
        }
        return mapToListPage(driverRepository.findAll(DriverSpecification.search(request), pageable));
    }

    @Override
    @Transactional
    public void deleteDriver(Long id) {
        log.debug("Attempt to delete driver with id: {}", id);
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Driver id must be greater than 0");
        }

        if (!driverRepository.existsById(id)) {
            throw new IllegalArgumentException("Driver with id " + id + " not found");
        }

        changeStatus(id, DriverStatus.INACTIVE);
        log.info("Driver with id: {} deleted successfully", id);
    }

    private DriverDetailsResponseDTO mapToDetails(Driver driver) {
        return new DriverDetailsResponseDTO(
                driver.getId(),
                driver.getFirstName(),
                driver.getLastName(),
                driver.getLicenseNumber(),
                driver.getPhoneNumber(),
                driver.getDriverStatus(),
                driver.getCreatedDate(),
                driver.getUpdatedDate()
        );
    }

    private Page<DriverListResponseDTO> mapToListPage(Page<Driver> drivers) {
        return drivers.map(driver -> new DriverListResponseDTO(
                driver.getId(),
                driver.getFirstName(),
                driver.getLastName(),
                driver.getLicenseNumber(),
                driver.getPhoneNumber(),
                driver.getDriverStatus()
        ));
    }
}
