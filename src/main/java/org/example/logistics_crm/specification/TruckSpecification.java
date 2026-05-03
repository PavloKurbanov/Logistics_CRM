package org.example.logistics_crm.specification;

import org.example.logistics_crm.dto.truck.request.SearchTruckRequestDTO;
import org.example.logistics_crm.entity.truck.Truck;
import org.example.logistics_crm.entity.truck.Truck_;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

public final class TruckSpecification {
    private TruckSpecification() {
    }

    public static Specification<Truck> search(SearchTruckRequestDTO request) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.model() != null && !request.model().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get(Truck_.model)),
                                request.model().toLowerCase() + "%"
                        )
                );
            }

            if (request.brand() != null && !request.brand().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get(Truck_.brand)),
                                request.brand().toLowerCase() + "%"
                        )
                );
            }

            if (request.licenseNumber() != null && !request.licenseNumber().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get(Truck_.licenseNumber)),
                                request.licenseNumber().toLowerCase() + "%"
                        )
                );
            }

            if (request.capacityFrom() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get(Truck_.capacity), request.capacityFrom())
                );
            }

            if (request.capacityTo() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get(Truck_.capacity), request.capacityTo())
                );
            }

            if (request.createdFrom() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get(Truck_.createdDate), request.createdFrom())
                );
            }

            if (request.createdTo() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get(Truck_.createdDate), request.createdTo())
                );
            }

            if (predicates.isEmpty()) {
                return cb.conjunction();
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        });
    }
}
