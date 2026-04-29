package org.example.logistics_crm.specification;

import org.example.logistics_crm.entity.client.Client;
import org.example.logistics_crm.dto.client.request.ClientSearchRequestDTO;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public final class ClientSpecification {
    private ClientSpecification() {
    }

    public static Specification<Client> search(ClientSearchRequestDTO request) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.firstName() != null && !request.firstName().isBlank()) {
                predicates.add(
                        cb.like(cb.lower(root.get("firstName")),
                                request.firstName().toLowerCase() + "%"
                        ));
            }

            if (request.lastName() != null && !request.lastName().isBlank()) {
                predicates.add(
                        cb.like(cb.lower(root.get("lastName")),
                                request.lastName().toLowerCase() + "%"
                        ));
            }

            if (request.email() != null && !request.email().isBlank()) {
                predicates.add(
                        cb.equal(root.get("email"), request.email())
                );
            }

            if (request.phoneNumber() != null && !request.phoneNumber().isBlank()) {
                predicates.add(
                        cb.equal(root.get("phoneNumber"), request.phoneNumber())
                );
            }

            if (request.createdFrom() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("createdDate"), request.createdFrom())
                );
            }

            if (request.createdTo() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("createdDate"), request.createdTo())
                );
            }

            if (predicates.isEmpty()) {
                return cb.conjunction();
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        });
    }
}
