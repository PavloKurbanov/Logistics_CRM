package org.example.logistics_crm.controller;

import jakarta.validation.Valid;
import org.example.logistics_crm.dto.truck.request.CreateTruckRequestDTO;
import org.example.logistics_crm.dto.truck.request.SearchTruckRequestDTO;
import org.example.logistics_crm.dto.truck.response.TruckDetailsResponseDTO;
import org.example.logistics_crm.dto.truck.response.TruckListResponseDTO;
import org.example.logistics_crm.service.truck.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trucks")
public class TruckController {

    private final TruckService truckService;

    @Autowired
    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TruckDetailsResponseDTO createTruck(
            @RequestBody @Valid CreateTruckRequestDTO createTruckRequestDTO) {
        return truckService.createTruck(createTruckRequestDTO);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<TruckListResponseDTO> getTrucks(Pageable pageable) {
        return truckService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TruckDetailsResponseDTO getTruckById(
            @PathVariable Long id) {
        return truckService.findById(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<TruckListResponseDTO> searchTrucks(
            @ModelAttribute SearchTruckRequestDTO search, Pageable pageable) {
        return truckService.searchTruck(search, pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTruckById(
            @PathVariable Long id) {
        truckService.deleteTruck(id);
    }
}
