package org.example.logistics_crm.controller;

import jakarta.validation.Valid;
import org.example.logistics_crm.dto.driver.request.CreateDriverRequestDTO;
import org.example.logistics_crm.dto.driver.request.DriverSearchRequestDTO;
import org.example.logistics_crm.dto.driver.response.DriverDetailsResponseDTO;
import org.example.logistics_crm.dto.driver.response.DriverListResponseDTO;
import org.example.logistics_crm.entity.driver.DriverStatus;
import org.example.logistics_crm.service.driver.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/drivers")
public class DriverController {
    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDetailsResponseDTO createDriver(
            @RequestBody @Valid CreateDriverRequestDTO requestDTO) {
        return driverService.createDriver(requestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDriver(
            @PathVariable("id") Long id) {
        driverService.deleteDriver(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<DriverListResponseDTO> searchDrivers(
            @ModelAttribute DriverSearchRequestDTO driverSearchRequestDTO, Pageable pageable) {
        return driverService.search(driverSearchRequestDTO, pageable);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<DriverListResponseDTO> getAllDrivers(Pageable pageable) {
        return driverService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DriverDetailsResponseDTO getDriverById(
            @PathVariable("id") Long id) {
        return driverService.findById(id);
    }

    @PatchMapping("/changeStatus/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public DriverDetailsResponseDTO changeDriverStatus(
            @PathVariable("id") Long id, @RequestParam DriverStatus status) {
        return driverService.changeStatus(id, status);
    }
}
