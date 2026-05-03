package org.example.logistics_crm.service.truck.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.example.logistics_crm.dto.truck.request.CreateTruckRequestDTO;
import org.example.logistics_crm.dto.truck.request.SearchTruckRequestDTO;
import org.example.logistics_crm.dto.truck.response.TruckDetailsResponseDTO;
import org.example.logistics_crm.dto.truck.response.TruckListResponseDTO;
import org.example.logistics_crm.entity.truck.Truck;
import org.example.logistics_crm.entity.truck.TruckStatus;
import org.example.logistics_crm.repository.TruckRepository;
import org.example.logistics_crm.service.truck.TruckService;
import org.example.logistics_crm.specification.TruckSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TruckServiceImpl implements TruckService {

    private final TruckRepository truckRepository;

    @Autowired
    public TruckServiceImpl(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    @Override
    @Transactional
    public TruckDetailsResponseDTO createTruck(CreateTruckRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("Create truck request must not be null");
        }

        log.debug("Attempting to create new truck with license number: {}", request.licenseNumber());

        if (existsByLicenseNumber(request.licenseNumber())) {
            throw new IllegalArgumentException("Truck with license number " + request.licenseNumber() + " already exists");
        }

        Truck truck = truckRepository.save(new Truck(
                request.brand(),
                request.model(),
                request.licenseNumber(),
                request.capacity()
        ));

        log.info("Truck with id: {} and license number: {} successfully created", truck.getId(), truck.getLicenseNumber());
        return pageToDetails(truck);
    }

    @Override
    @Transactional(readOnly = true)
    public TruckDetailsResponseDTO findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Truck id must be greater than 0");
        }

        log.debug("Fetching truck with id: {}", id);

        Truck truck = truckRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Truck with id " + id + " does not exist"));

        return pageToDetails(truck);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TruckListResponseDTO> findAll(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide pagination parameters.");
        }
        log.debug("Fetching all trucks in page: {}", pageable);
        return pageToList(truckRepository.findAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TruckListResponseDTO> searchTruck(SearchTruckRequestDTO requestDTO, Pageable pageable) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("Truck search request can't be null");
        }

        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide pagination parameters.");
        }

        log.debug("Searching for trucks with criteria: {}", requestDTO);
        return pageToList(truckRepository.findAll(TruckSpecification.search(requestDTO), pageable));
    }

    @Override
    @Transactional
    public void deleteTruck(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Truck id must be greater than 0");
        }

        log.debug("Attempting to delete truck with id: {}", id);

        Truck truck = truckRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Truck with id " + id + " does not exist"));

        if (truck.getTruckStatus() != TruckStatus.AVAILABLE) {
            throw new IllegalStateException("Truck with id " + id + " cannot be deleted because it is not available");
        }
        truckRepository.delete(truck);
        log.info("Truck with id: {} successfully deleted", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByLicenseNumber(String licenseNumber) {
        if (licenseNumber == null) {
            throw new IllegalArgumentException("License number must not be null");
        }
        return truckRepository.existsByLicenseNumber(licenseNumber);
    }

    private TruckDetailsResponseDTO pageToDetails(Truck truck) {
        return new TruckDetailsResponseDTO(
                truck.getId(),
                truck.getBrand(),
                truck.getModel(),
                truck.getLicenseNumber(),
                truck.getCapacity(),
                truck.getTruckStatus(),
                truck.getCreatedDate(),
                truck.getUpdatedDate()
        );
    }

    private Page<TruckListResponseDTO> pageToList(Page<Truck> trucks) {
        return trucks
                .map(truck -> new TruckListResponseDTO(
                        truck.getId(),
                        truck.getBrand(),
                        truck.getModel(),
                        truck.getLicenseNumber(),
                        truck.getCapacity(),
                        truck.getTruckStatus()
                ));
    }
}
