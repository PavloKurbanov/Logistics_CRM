package org.example.logistics_crm.service.driver;

import org.example.logistics_crm.dto.driver.request.CreateDriverRequestDTO;
import org.example.logistics_crm.dto.driver.request.DriverSearchRequestDTO;
import org.example.logistics_crm.dto.driver.response.DriverDetailsResponseDTO;
import org.example.logistics_crm.dto.driver.response.DriverListResponseDTO;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public interface DriverService {

    DriverDetailsResponseDTO createDriver(CreateDriverRequestDTO createDriverRequestDTO);

    DriverDetailsResponseDTO findById(Long id);

    Page<DriverListResponseDTO> findAll(Pageable pageable);

    Page<DriverListResponseDTO> search(DriverSearchRequestDTO request, Pageable pageable);
}
