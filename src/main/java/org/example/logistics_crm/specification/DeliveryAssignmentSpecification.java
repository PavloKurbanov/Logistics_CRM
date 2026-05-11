package org.example.logistics_crm.specification;


import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.example.logistics_crm.dto.deliveryAssignment.request.DeliveryAssignmentSearchRequestDTO;
import org.example.logistics_crm.entity.deliveryAssignment.DeliveryAssignment;
import org.example.logistics_crm.entity.deliveryAssignment.DeliveryAssignment_;
import org.example.logistics_crm.entity.deliveryAssignment.DeliveryStatus;
import org.example.logistics_crm.entity.driver.Driver_;
import org.example.logistics_crm.entity.order.Order_;
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

            if (searchRequest.orderId() != null) {
                predicates.add(cb.equal(root.get(DeliveryAssignment_.order).get(Order_.id), searchRequest.orderId()));
            }

            if (searchRequest.driverId() != null) {
                predicates.add(cb.equal(root.get(DeliveryAssignment_.driver).get(Driver_.id), searchRequest.driverId()));
            }

            if (searchRequest.truckId() != null) {
                predicates.add(cb.equal(root.get(DeliveryAssignment_.truck).get(Truck_.id), searchRequest.truckId()));
            }

            if(searchRequest.status() != null && !searchRequest.status().isBlank()){
                try{
                    DeliveryStatus status = DeliveryStatus.valueOf(searchRequest.status().toUpperCase());
                    predicates.add(cb.equal(root.get(DeliveryAssignment_.deliveryStatus), status));
                } catch (IllegalArgumentException e) {
                    log.warn("Invalid status provided in search: {}", searchRequest.status().toUpperCase());
                }
            }

            if(searchRequest.orderCode() != null && !searchRequest.orderCode().isBlank()){
                predicates.add(cb.like(
                        cb.lower(root.get(DeliveryAssignment_.order).get(Order_.orderCode)), searchRequest.orderCode()));
            }

            if(searchRequest.createdDateFrom() != null){
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get(DeliveryAssignment_.createdDate), searchRequest.createdDateFrom()));
            }

            if(searchRequest.createdDateTo() != null){
                predicates.add(
                        cb.lessThanOrEqualTo(root.get(DeliveryAssignment_.createdDate), searchRequest.createdDateTo()));
            }



            return cb.and(predicates.toArray(Predicate[]::new));
        });
    }
}
