package org.example.logistics_crm.controller;

import jakarta.validation.Valid;
import org.example.logistics_crm.dto.deliveryAssignment.request.CreateDeliveryAssignmentRequestDTO;
import org.example.logistics_crm.dto.deliveryAssignment.request.DeliveryAssignmentSearchRequestDTO;
import org.example.logistics_crm.dto.deliveryAssignment.response.DeliveryAssignmentDetailsResponseDTO;
import org.example.logistics_crm.service.deliveryAssignment.DeliveryAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/deliveryAssignments")
public class DeliveryAssignmentController {
    private final DeliveryAssignmentService deliveryAssignmentService;

    @Autowired
    public DeliveryAssignmentController(DeliveryAssignmentService deliveryAssignmentService) {
        this.deliveryAssignmentService = deliveryAssignmentService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public DeliveryAssignmentDetailsResponseDTO createDeliveryAssignment(
            @RequestBody @Valid CreateDeliveryAssignmentRequestDTO requestDTO) {
        return deliveryAssignmentService.createDeliveryAssignment(requestDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeliveryAssignmentDetailsResponseDTO findDeliveryAssignmentById(
            @PathVariable("id") Long id) {
        return deliveryAssignmentService.findDeliveryAssignmentById(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<DeliveryAssignmentDetailsResponseDTO> findDeliveryAssignmentBySearch(
            DeliveryAssignmentSearchRequestDTO searchRequestDTO, Pageable pageable) {
        return deliveryAssignmentService.search(searchRequestDTO, pageable);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<DeliveryAssignmentDetailsResponseDTO> findAllDeliveryAssignments(
            Pageable pageable){
        return deliveryAssignmentService.findAllDeliveryAssignments(pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeliveryAssignment(
            @PathVariable("id") Long id){
        deliveryAssignmentService.deleteDeliveryAssignment(id);
    }
}
