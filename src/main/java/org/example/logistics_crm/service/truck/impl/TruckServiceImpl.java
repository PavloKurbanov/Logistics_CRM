package org.example.logistics_crm.service.truck.impl;

import jakarta.transaction.Transactional;
import org.example.logistics_crm.dto.truck.request.CreateTruckRequestDTO;
import org.example.logistics_crm.dto.truck.request.SearchTruckRequestDTO;
import org.example.logistics_crm.dto.truck.response.TruckDetailsResponseDTO;
import org.example.logistics_crm.dto.truck.response.TruckListResponseDTO;
import org.example.logistics_crm.entity.truck.Truck;
import org.example.logistics_crm.repository.TruckRepository;
import org.example.logistics_crm.service.truck.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        if (existsByLicenseNumber(request.licenseNumber())) {
            throw new IllegalArgumentException("Truck with license number " + request.licenseNumber() + " already exists");
        }

        Truck truck = truckRepository.save(new Truck(
                request.brand(),
                request.model(),
                request.licenseNumber(),
                request.capacity()
        ));

        return pageToDetails(truck);
    }

    @Override
    public TruckDetailsResponseDTO findById(Long id) {
        Truck truck = truckRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Truck with id " + id + " does not exist"));
        return pageToDetails(truck);
    }

    @Override
    public Page<TruckListResponseDTO> findAll(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide pagination parameters.");
        }
        return pageToList(truckRepository.findAll(pageable));
    }

    @Override
    public Page<TruckListResponseDTO> searchTruck(SearchTruckRequestDTO requestDTO, Pageable pageable) {
        return null;
    }

    @Override
    @Transactional
    public void deleteTruck(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Truck id must be greater than 0");
        }
        Truck truck = truckRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Truck with id " + id + " does not exist"));

        truckRepository.delete(truck);
    }

    @Override
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
