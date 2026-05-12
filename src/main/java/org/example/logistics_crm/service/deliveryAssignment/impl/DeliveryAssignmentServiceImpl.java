package org.example.logistics_crm.service.deliveryAssignment.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.logistics_crm.dto.deliveryAssignment.request.CreateDeliveryAssignmentRequestDTO;
import org.example.logistics_crm.dto.deliveryAssignment.response.DeliveryAssignmentDetailsResponseDTO;
import org.example.logistics_crm.dto.deliveryAssignment.request.DeliveryAssignmentSearchRequestDTO;
import org.example.logistics_crm.entity.deliveryAssignment.DeliveryAssignment;
import org.example.logistics_crm.entity.deliveryAssignment.DeliveryStatus;
import org.example.logistics_crm.entity.driver.Driver;
import org.example.logistics_crm.entity.order.Order;
import org.example.logistics_crm.entity.truck.Truck;
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
        validateAssignmentPossibility(requestDTO);

        log.debug("Attempting to create delivery assignment with Order_ID: {}, Driver_ID: {}, Truck_ID: {} ",
                requestDTO.orderId(), requestDTO.driverId(), requestDTO.truckId());

        DeliveryAssignment deliveryAssignment = new DeliveryAssignment();

        Driver driver = driverService.findDriverEntityById(requestDTO.driverId());
        Order order = orderService.findOrderEntityById(requestDTO.orderId());
        Truck truck = truckService.findTruckEntityById(requestDTO.truckId());

        deliveryAssignment.setDriver(driver);
        deliveryAssignment.setOrder(order);
        deliveryAssignment.setTruck(truck);

//        deliveryAssignment.setDeliveryStatus(DeliveryStatus.PLANNED);
//        driver.setDriverStatus(DriverStatus.ON_DELIVERY);
//        order.setOrderStatus(OrderStatus.CONFIRMED);
//        truck.setTruckStatus(TruckStatus.BUSY);

        DeliveryAssignment savedAssignment = deliveryAssignmentRepository.save(deliveryAssignment);

        log.info("Delivery assignment created with ID: {}", savedAssignment.getId());

        return mapToDetails(savedAssignment);
    }

    @Override
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
    public Page<DeliveryAssignmentDetailsResponseDTO> findDeliveryAssignmentByStatus(DeliveryStatus deliveryStatus, Pageable pageable) {
        if (deliveryStatus == null) {
            throw new IllegalArgumentException("Delivery status must not be null");
        }
        log.debug("Attempting to find delivery assignment by status: {}", deliveryStatus);
        return deliveryAssignmentRepository.findAllByDeliveryStatus(deliveryStatus, pageable).map(this::mapToDetails);
    }

    @Override
    public Page<DeliveryAssignmentDetailsResponseDTO> findAllDeliveryAssignments(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide pagination parameters.");
        }
        log.debug("Fetching all delivery assignment page request:  {}", pageable);
        return deliveryAssignmentRepository.findAll(pageable).map(this::mapToDetails);
    }

    @Override
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

    private void validateAssignmentPossibility(CreateDeliveryAssignmentRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("Request Delivery Assignment cannot be null");
        }

        List<DeliveryStatus> deliveryStatuses = List.of(DeliveryStatus.PLANNED, DeliveryStatus.IN_PROGRESS);

        if (deliveryAssignmentRepository.existsByOrderIdAndDeliveryStatusIn(requestDTO.orderId(), deliveryStatuses)) {
            throw new IllegalArgumentException("Order with id: " + requestDTO.orderId() + " already has an active delivery assignment");
        }

        if (deliveryAssignmentRepository.existsByDriverIdAndDeliveryStatus(requestDTO.driverId(), DeliveryStatus.IN_PROGRESS)) {
            throw new IllegalArgumentException("Driver with id: " + requestDTO.driverId() + " is currently on delivery and cannot be assigned to another delivery");
        }

        if (deliveryAssignmentRepository.existsByTruckIdAndDeliveryStatus(requestDTO.truckId(), DeliveryStatus.IN_PROGRESS)) {
            throw new IllegalArgumentException("Truck with id: " + requestDTO.truckId() + " is currently on delivery and cannot be assigned to another delivery");
        }
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
}
