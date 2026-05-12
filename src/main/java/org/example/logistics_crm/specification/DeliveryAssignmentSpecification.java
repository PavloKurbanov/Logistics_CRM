package org.example.logistics_crm.specification;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.example.logistics_crm.dto.deliveryAssignment.request.DeliveryAssignmentSearchRequestDTO;
import org.example.logistics_crm.entity.client.Client;
import org.example.logistics_crm.entity.client.Client_;
import org.example.logistics_crm.entity.deliveryAssignment.DeliveryAssignment;
import org.example.logistics_crm.entity.deliveryAssignment.DeliveryAssignment_;
import org.example.logistics_crm.entity.deliveryAssignment.DeliveryStatus;
import org.example.logistics_crm.entity.driver.Driver;
import org.example.logistics_crm.entity.driver.Driver_;
import org.example.logistics_crm.entity.order.Order;
import org.example.logistics_crm.entity.order.Order_;
import org.example.logistics_crm.entity.truck.Truck;
import org.example.logistics_crm.entity.truck.Truck_;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class DeliveryAssignmentSpecification {
    private DeliveryAssignmentSpecification() {
    }

    public static Specification<DeliveryAssignment> search(DeliveryAssignmentSearchRequestDTO searchRequest) {
        return ((root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (hasDriverFilter(searchRequest)) {
                Join<DeliveryAssignment, Driver> join = root.join(DeliveryAssignment_.driver);
                addDriverPredicate(predicates, cb, join, searchRequest);
            }

            if (hasTruckFilter(searchRequest)) {
                Join<DeliveryAssignment, Truck> join = root.join(DeliveryAssignment_.truck);
                addTruckPredicate(predicates, cb, join, searchRequest);
            }

            if (hasOrderFilter(searchRequest)) {
                Join<DeliveryAssignment, Order> join = root.join(DeliveryAssignment_.order);
                addOrderPredicate(predicates, cb, join, searchRequest);
            }

            addBasePredicates(predicates, cb, root, searchRequest);

            return cb.and(predicates.toArray(Predicate[]::new));
        });
    }

    private static void addBasePredicates(List<Predicate> predicates, CriteriaBuilder cb,
                                          Root<DeliveryAssignment> root, DeliveryAssignmentSearchRequestDTO searchRequest) {
        if (isNotBlank(searchRequest.status())) {
            try {
                DeliveryStatus status = DeliveryStatus.valueOf(searchRequest.status());
                predicates.add(cb.equal(root.get(DeliveryAssignment_.deliveryStatus), status));
            } catch (IllegalArgumentException e) {
                log.warn("Invalid status: {}", searchRequest.status());
            }
        }

        if (searchRequest.createdDateFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(DeliveryAssignment_.createdDate), searchRequest.createdDateFrom()));
        }

        if (searchRequest.createdDateTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(DeliveryAssignment_.createdDate), searchRequest.createdDateTo()));
        }
    }

    private static void addDriverPredicate(List<Predicate> predicates, CriteriaBuilder cb,
                                           Join<DeliveryAssignment, Driver> driverJoin, DeliveryAssignmentSearchRequestDTO searchRequest) {
        if (searchRequest.driverId() != null) {
            predicates.add(cb.equal(driverJoin.get(Driver_.id), searchRequest.driverId()));
        }

        if (isNotBlank(searchRequest.driverFullName())) {
            String driverFullName = searchRequest.driverFullName().toLowerCase() + "%";
            predicates.add(cb.or(
                    cb.like(cb.lower(driverJoin.get(Driver_.firstName)), driverFullName),
                    cb.like(cb.lower(driverJoin.get(Driver_.lastName)), driverFullName)
            ));
        }
    }

    private static void addTruckPredicate(List<Predicate> predicates, CriteriaBuilder cb,
                                          Join<DeliveryAssignment, Truck> truckJoin, DeliveryAssignmentSearchRequestDTO searchRequest) {
        if (searchRequest.truckId() != null) {
            predicates.add(cb.equal(truckJoin.get(Truck_.id), searchRequest.truckId()));
        }

        if (isNotBlank(searchRequest.truckLicensePlate())) {
            predicates.add(cb.like(cb.lower(truckJoin.get(Truck_.licenseNumber)),
                    searchRequest.truckLicensePlate().toLowerCase() + "%"));
        }
    }

    private static void addOrderPredicate(List<Predicate> predicates, CriteriaBuilder cb,
                                          Join<DeliveryAssignment, Order> orderJoin, DeliveryAssignmentSearchRequestDTO searchRequest) {
        if (searchRequest.orderId() != null) {
            predicates.add(cb.equal(orderJoin.get(Order_.id), searchRequest.orderId()));
        }

        if (searchRequest.orderCode() != null && !searchRequest.orderCode().isBlank()) {
            predicates.add(cb.like(
                    cb.lower(orderJoin.get(Order_.orderCode)), searchRequest.orderCode().toLowerCase() + "%"));
        }

        if (isNotBlank(searchRequest.senderFirstName()) || isNotBlank(searchRequest.senderLastName())) {
            Join<Order, Client> join = orderJoin.join(Order_.senderClient);

            if (isNotBlank(searchRequest.senderFirstName())) {
                predicates.add(cb.like(cb.lower(join.get(Client_.firstName)), searchRequest.senderFirstName().toLowerCase() + "%"));
            }

            if (isNotBlank(searchRequest.senderLastName())) {
                predicates.add(cb.like(cb.lower(join.get(Client_.lastName)), searchRequest.senderLastName().toLowerCase() + "%"));
            }
        }

        if (isNotBlank(searchRequest.recipientFirstName()) || isNotBlank(searchRequest.recipientLastName())) {
            Join<Order, Client> join = orderJoin.join(Order_.receiverClient);

            if (isNotBlank(searchRequest.recipientFirstName())) {
                predicates.add(cb.like(cb.lower(join.get(Client_.firstName)), searchRequest.recipientFirstName().toLowerCase() + "%"));
            }

            if (isNotBlank(searchRequest.recipientLastName())) {
                predicates.add(cb.like(cb.lower(join.get(Client_.lastName)), searchRequest.recipientLastName().toLowerCase() + "%"));
            }
        }
    }

    private static boolean hasOrderFilter(DeliveryAssignmentSearchRequestDTO orderRequest) {
        return orderRequest.orderId() != null || isNotBlank(orderRequest.orderCode()) || isNotBlank(orderRequest.senderFirstName())
                || isNotBlank(orderRequest.senderLastName()) || isNotBlank(orderRequest.recipientFirstName())
                || isNotBlank(orderRequest.recipientLastName());
    }

    private static boolean hasTruckFilter(DeliveryAssignmentSearchRequestDTO truckRequest) {
        return truckRequest.truckId() != null || isNotBlank(truckRequest.truckLicensePlate());
    }

    private static boolean hasDriverFilter(DeliveryAssignmentSearchRequestDTO driverRequest) {
        return driverRequest.driverId() != null || isNotBlank(driverRequest.driverFullName());
    }

    public static boolean isNotBlank(String str) {
        return str != null && !str.isBlank();
    }
}
