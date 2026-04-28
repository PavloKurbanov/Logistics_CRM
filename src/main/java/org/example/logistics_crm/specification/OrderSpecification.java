package org.example.logistics_crm.specification;

import jakarta.persistence.criteria.Predicate;
import org.example.logistics_crm.entity.order.Order;
import org.example.logistics_crm.dto.order.request.OrderSearchRequestDTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class OrderSpecification {

    private OrderSpecification() {}

    public static Specification<Order> search(OrderSearchRequestDTO request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.senderFirstName() != null && !request.senderFirstName().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("senderClient").get("firstName")),
                                "%" + request.senderFirstName().toLowerCase() + "%"
                        )
                );
            }

            if (request.senderLastName() != null && !request.senderLastName().isBlank()) {
                predicates.add(
                        cb.like(cb.lower(root.get("senderClient").get("lastName")), "%"
                                + request.senderLastName().toLowerCase() + "%")
                );
            }

            if (request.receiverFirstName() != null && !request.receiverFirstName().isBlank()) {
                predicates.add(
                        cb.like(cb.lower(root.get("receiverClient").get("firstName")), "%"
                                + request.receiverFirstName().toLowerCase() + "%")
                );
            }

            if (request.receiverLastName() != null && !request.receiverLastName().isBlank()) {
                predicates.add(
                        cb.like(cb.lower(root.get("receiverClient").get("lastName")), "%"
                                + request.receiverLastName().toLowerCase() + "%"
                        )
                );
            }

            if (request.status() != null) {
                predicates.add(
                        cb.equal(root.get("orderStatus"), request.status())
                );
            }

            if (request.minPrice() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("price"), request.minPrice())
                );
            }

            if (request.maxPrice() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("price"), request.maxPrice())
                );
            }

            if (request.minWeight() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("weight"), request.minWeight())
                );
            }

            if (request.maxWeight() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("weight"), request.maxWeight())
                );
            }

            if (request.startDate() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("deliveryDate"), request.startDate())
                );
            }

            if (request.endDate() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("deliveryDate"), request.endDate())
                );
            }

            if (request.pickupAddress() != null && !request.pickupAddress().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("pickupAddress")),
                                "%" + request.pickupAddress().toLowerCase() + "%"
                        )
                );
            }

            if (request.deliveryAddress() != null && !request.deliveryAddress().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("deliveryAddress")),
                                "%" + request.deliveryAddress().toLowerCase() + "%"
                        )
                );
            }

            if(predicates.isEmpty()){
                cb.conjunction();
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}

//cb.equal(...)                  // =
//cb.like(...)                   // LIKE
//cb.greaterThan(...)            // >
//cb.greaterThanOrEqualTo(...)   // >=
//cb.lessThan(...)               // <
//cb.lessThanOrEqualTo(...)      // <=