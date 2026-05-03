package org.example.logistics_crm.service.deliveryAssignment.impl;

import org.example.logistics_crm.dto.deliveryAssignment.CreateDeliveryAssignmentRequestDTO;
import org.example.logistics_crm.dto.deliveryAssignment.DeliveryAssignmentDetailsResponseDTO;
import org.example.logistics_crm.dto.deliveryAssignment.DeliveryAssignmentSearchRequestDTO;
import org.example.logistics_crm.repository.DeliveryAssignmentRepository;
import org.example.logistics_crm.service.deliveryAssignment.DeliveryAssignmentService;
import org.example.logistics_crm.service.driver.DriverService;
import org.example.logistics_crm.service.order.OrderService;
import org.example.logistics_crm.service.truck.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DeliveryAssignmentServiceImpl implements DeliveryAssignmentService {
    private final DriverService driverService;
    private final OrderService orderService;
    private final TruckService truckService;
    private final DeliveryAssignmentRepository deliveryAssignmentRepository;

    @Autowired
    public DeliveryAssignmentServiceImpl(DriverService driverService, OrderService orderService,
                                         TruckService truckService, DeliveryAssignmentRepository deliveryAssignmentRepository) {
        this.driverService = driverService;
        this.orderService = orderService;
        this.truckService = truckService;
        this.deliveryAssignmentRepository = deliveryAssignmentRepository;
    }


    @Override
    public DeliveryAssignmentDetailsResponseDTO createDeliveryAssignment(CreateDeliveryAssignmentRequestDTO requestDTO) {

        return null;
    }

    @Override
    public DeliveryAssignmentDetailsResponseDTO findDeliveryAssignmentById(Long deliveryAssignmentId) {
        return null;
    }

    @Override
    public DeliveryAssignmentDetailsResponseDTO findDeliveryAssignmentByStatus(Long deliveryAssignmentId, Long deliveryId) {
        return null;
    }

    @Override
    public Page<DeliveryAssignmentDetailsResponseDTO> findAllDeliveryAssignments(Pageable pageable) {
        return null;
    }

    @Override
    public Page<DeliveryAssignmentDetailsResponseDTO> search(DeliveryAssignmentSearchRequestDTO requestDTO, Pageable pageable) {
        return null;
    }
}
