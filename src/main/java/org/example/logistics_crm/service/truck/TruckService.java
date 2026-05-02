package org.example.logistics_crm.service.truck;

import org.example.logistics_crm.dto.truck.request.CreateTruckRequestDTO;
import org.example.logistics_crm.dto.truck.request.SearchTruckRequestDTO;
import org.example.logistics_crm.dto.truck.response.TruckDetailsResponseDTO;
import org.example.logistics_crm.dto.truck.response.TruckListResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TruckService {
    TruckDetailsResponseDTO createTruck(CreateTruckRequestDTO request);

    TruckDetailsResponseDTO findById(Long id);

    Page<TruckListResponseDTO> findAll(Pageable pageable);

    Page<TruckListResponseDTO> searchTruck(SearchTruckRequestDTO requestDTO, Pageable pageable);

    void deleteTruck(Long id);

    boolean existsByLicenseNumber(String licenseNumber);
}
