package org.example.logistics_crm.service.driver;

import org.example.logistics_crm.dto.driver.request.CreateDriverRequestDTO;
import org.example.logistics_crm.dto.driver.request.DriverSearchRequestDTO;
import org.example.logistics_crm.dto.driver.response.DriverDetailsResponseDTO;
import org.example.logistics_crm.dto.driver.response.DriverListResponseDTO;
import org.example.logistics_crm.entity.driver.DriverStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DriverService {

    DriverDetailsResponseDTO createDriver(CreateDriverRequestDTO createDriverRequestDTO);

    DriverDetailsResponseDTO changeStatus(Long id, DriverStatus driverStatus);

    DriverDetailsResponseDTO findById(Long id);

    Page<DriverListResponseDTO> findAll(Pageable pageable);

    Page<DriverListResponseDTO> search(DriverSearchRequestDTO request, Pageable pageable);

    void deleteDriver(Long id);

}
