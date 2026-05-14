package org.example.logistics_crm.service.deliveryAssignment.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.logistics_crm.dto.deliveryAssignment.request.CreateDeliveryAssignmentRequestDTO;
import org.example.logistics_crm.dto.deliveryAssignment.response.DeliveryAssignmentDetailsResponseDTO;
import org.example.logistics_crm.dto.deliveryAssignment.request.DeliveryAssignmentSearchRequestDTO;
import org.example.logistics_crm.entity.deliveryAssignment.DeliveryAssignment;
import org.example.logistics_crm.entity.deliveryAssignment.DeliveryStatus;
import org.example.logistics_crm.entity.driver.Driver;
import org.example.logistics_crm.entity.driver.DriverStatus;
import org.example.logistics_crm.entity.order.Order;
import org.example.logistics_crm.entity.order.OrderStatus;
import org.example.logistics_crm.entity.truck.Truck;
import org.example.logistics_crm.entity.truck.TruckStatus;
import org.example.logistics_crm.repository.DeliveryAssignmentRepository;
import org.example.logistics_crm.service.deliveryAssignment.DeliveryAssignmentService;
import org.example.logistics_crm.service.driver.DriverService;
import org.example.logistics_crm.service.order.OrderService;
import org.example.logistics_crm.service.truck.TruckService;
import org.example.logistics_crm.specification.DeliveryAssignmentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Slf4j
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
    @Transactional
    public DeliveryAssignmentDetailsResponseDTO createDeliveryAssignment(CreateDeliveryAssignmentRequestDTO requestDTO) {


        log.debug("Attempting to create delivery assignment with Order_ID: {}, Driver_ID: {}, Truck_ID: {} ",
                requestDTO.orderId(), requestDTO.driverId(), requestDTO.truckId());

        Driver driver = driverService.findDriverEntityById(requestDTO.driverId());
        Order order = orderService.findOrderEntityById(requestDTO.orderId());
        Truck truck = truckService.findTruckEntityById(requestDTO.truckId());

        if (!validateEntity(driver, order, truck)) {
            throw new InvalidParameterException("Driver or Truck ID is invalid");
        }

        log.debug("Validation and resource status check for Driver: {}, Truck: {}, Order: {}",
                driver.getId(), truck.getId(), order.getId());

        validateResourcesAvailability(driver, order, truck);

        DeliveryAssignment deliveryAssignment = new DeliveryAssignment();
        deliveryAssignment.setDriver(driver);
        deliveryAssignment.setOrder(order);
        deliveryAssignment.setTruck(truck);

        deliveryAssignment.setDeliveryStatus(DeliveryStatus.PLANNED);
        updateResourceStatuses(driver, order, truck);

        DeliveryAssignment savedAssignment = deliveryAssignmentRepository.save(deliveryAssignment);

        log.info("Successfully created assignment with ID: {}", savedAssignment.getId());

        return mapToDetails(savedAssignment);
    }

    @Override
    @Transactional(readOnly = true)
    public DeliveryAssignmentDetailsResponseDTO findDeliveryAssignmentById(Long deliveryAssignmentId) {
        if (deliveryAssignmentId == null || deliveryAssignmentId <= 0) {
            throw new IllegalArgumentException("Delivery assignment id must be greater than 0");
        }

        log.debug("Attempting to find delivery assignment with ID: {}", deliveryAssignmentId);

        return deliveryAssignmentRepository.findById(deliveryAssignmentId).map(this::mapToDetails)
                .orElseThrow(() -> new IllegalArgumentException("Delivery assignment with id " + deliveryAssignmentId +
                        " does not exist"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeliveryAssignmentDetailsResponseDTO> findAllDeliveryAssignments(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide pagination parameters.");
        }
        log.debug("Fetching all delivery assignment page request:  {}", pageable);
        return deliveryAssignmentRepository.findAll(pageable).map(this::mapToDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeliveryAssignmentDetailsResponseDTO> search(DeliveryAssignmentSearchRequestDTO requestDTO, Pageable pageable) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("Delivery assignment search cannot be null");
        }
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide pagination parameters.");
        }
        log.debug("Searching for delivery assignment with criteria: {}", requestDTO);
        return deliveryAssignmentRepository.findAll(DeliveryAssignmentSpecification.search(requestDTO), pageable).map(this::mapToDetails);
    }

    @Transactional
    public void deleteDeliveryAssignment(Long deliveryAssignmentId) {
        if (deliveryAssignmentId == null || deliveryAssignmentId <= 0) {
            throw new IllegalArgumentException("Delivery assignment id must be greater than 0");
        }
        log.debug("Attempting to delete delivery assignment with ID: {}", deliveryAssignmentId);

        DeliveryAssignment deliveryAssignment = deliveryAssignmentRepository.findById(deliveryAssignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery assignment with id " + deliveryAssignmentId + " does not exist"));

        deliveryAssignmentRepository.delete(deliveryAssignment);
        log.info("Delivery assignment with id: {} successfully deleted", deliveryAssignment.getId());
    }

    private void validateResourcesAvailability(Driver driver, Order order, Truck truck) {

        if (driver.getDriverStatus() != DriverStatus.AVAILABLE) {
            throw new IllegalArgumentException("Driver is not available. Current status: " + driver.getDriverStatus());
        }

        if (truck.getTruckStatus() != TruckStatus.AVAILABLE) {
            throw new IllegalArgumentException("Truck is not available. Current status: " + truck.getTruckStatus());
        }

        if (deliveryAssignmentRepository.existsByOrderIdAndDeliveryStatusIn(
                order.getId(), List.of(DeliveryStatus.PLANNED, DeliveryStatus.IN_PROGRESS))) {
            throw new IllegalArgumentException("Order already has an active assignment");
        }
    }

    private void updateResourceStatuses(Driver driver, Order order, Truck truck) {
        driver.setDriverStatus(DriverStatus.ON_DELIVERY);
        truck.setTruckStatus(TruckStatus.BUSY);
        order.setOrderStatus(OrderStatus.CONFIRMED);
    }

    private DeliveryAssignmentDetailsResponseDTO mapToDetails(DeliveryAssignment deliveryAssignment) {
        return new DeliveryAssignmentDetailsResponseDTO(
                deliveryAssignment.getId(),
                deliveryAssignment.getOrder().getId(),
                deliveryAssignment.getOrder().getOrderCode(),
                deliveryAssignment.getOrder().getSenderClient().getFirstName() + " " + deliveryAssignment.getOrder().getSenderClient().getLastName(),
                deliveryAssignment.getOrder().getReceiverClient().getFirstName() + " " + deliveryAssignment.getOrder().getReceiverClient().getLastName(),
                deliveryAssignment.getDriver().getId(),
                deliveryAssignment.getDriver().getFirstName() + " " + deliveryAssignment.getDriver().getLastName(),
                deliveryAssignment.getTruck().getId(),
                deliveryAssignment.getTruck().getLicenseNumber(),
                deliveryAssignment.getDeliveryStatus().name(),
                deliveryAssignment.getCreatedDate(),
                deliveryAssignment.getUpdatedDate()
        );
    }

    private boolean validateEntity(Driver driver, Order order, Truck truck) {
        return truck != null && driver != null && order != null;
    }
}
