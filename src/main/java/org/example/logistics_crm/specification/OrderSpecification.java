package org.example.logistics_crm.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.example.logistics_crm.entity.client.Client;
import org.example.logistics_crm.entity.client.Client_;
import org.example.logistics_crm.entity.order.Order;
import org.example.logistics_crm.dto.order.request.OrderSearchRequestDTO;
import org.example.logistics_crm.entity.order.Order_;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class OrderSpecification {

    private OrderSpecification() {
    }

    public static Specification<Order> search(OrderSearchRequestDTO request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<Order, Client> senderJoin = root.join(Order_.senderClient);
            Join<Order, Client> receiverJoin = root.join(Order_.receiverClient);


            if (request.senderFirstName() != null && !request.senderFirstName().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(senderJoin.get(Client_.firstName)),
                                request.senderFirstName().toLowerCase() + "%"
                        )
                );
            }

            if (request.senderLastName() != null && !request.senderLastName().isBlank()) {
                predicates.add(
                        cb.like(cb.lower(senderJoin.get(Client_.lastName)),
                                request.senderLastName().toLowerCase() + "%")
                );
            }

            if (request.receiverFirstName() != null && !request.receiverFirstName().isBlank()) {
                predicates.add(
                        cb.like(cb.lower(receiverJoin.get(Client_.firstName)),
                                request.receiverFirstName().toLowerCase() + "%")
                );
            }

            if (request.receiverLastName() != null && !request.receiverLastName().isBlank()) {
                predicates.add(
                        cb.like(cb.lower(receiverJoin.get(Client_.lastName)),
                                request.receiverLastName().toLowerCase() + "%"
                        )
                );
            }

            if (request.status() != null) {
                predicates.add(
                        cb.equal(root.get(Order_.orderStatus), request.status())
                );
            }

            if (request.minPrice() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get(Order_.price), request.minPrice())
                );
            }

            if (request.maxPrice() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get(Order_.price), request.maxPrice())
                );
            }

            if (request.minWeight() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get(Order_.weight), request.minWeight())
                );
            }

            if (request.maxWeight() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get(Order_.weight), request.maxWeight())
                );
            }

            if (request.startDate() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get(Order_.deliveryDate), request.startDate())
                );
            }

            if (request.endDate() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get(Order_.deliveryDate), request.endDate())
                );
            }

            if (request.pickupAddress() != null && !request.pickupAddress().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get(Order_.pickupAddress)),
                                request.pickupAddress().toLowerCase() + "%"
                        )
                );
            }

            if (request.deliveryAddress() != null && !request.deliveryAddress().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get(Order_.deliveryAddress)),
                                request.deliveryAddress().toLowerCase() + "%"
                        )
                );
            }

            if (predicates.isEmpty()) {
                return cb.conjunction();
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }


}