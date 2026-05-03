package org.example.logistics_crm.specification;

import jakarta.persistence.criteria.Predicate;
import org.example.logistics_crm.dto.driver.request.DriverSearchRequestDTO;
import org.example.logistics_crm.entity.driver.Driver;
import org.example.logistics_crm.entity.driver.Driver_;
import org.springframework.data.jpa.domain.Specification;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public final class DriverSpecification {
    private DriverSpecification() {
    }

    public static Specification<Driver> search(DriverSearchRequestDTO request) {
        return (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.firstName() != null && !request.firstName().isBlank()) {
                predicates.add(
                        cb.like(cb.lower(root.get(Driver_.firstName)),
                                request.firstName().toLowerCase() + "%"
                        )
                );
            }

            if (request.lastName() != null && !request.lastName().isBlank()) {
                predicates.add(
                        cb.like(cb.lower(root.get(Driver_.lastName)),
                                request.lastName().toLowerCase() + "%"
                        )
                );
            }

            if (request.licenseNumber() != null && !request.licenseNumber().isBlank()) {
                predicates.add(
                        cb.like(cb.lower(root.get(Driver_.licenseNumber)),
                                request.licenseNumber().toLowerCase() + "%"
                        )
                );
            }

            if(request.status() != null) {
                predicates.add(cb.equal(root.get(Driver_.driverStatus), request.status()));
            }

            if(request.phoneNumber() != null && !request.phoneNumber().isBlank()) {
                predicates.add(
                        cb.equal(root.get(Driver_.PHONE_NUMBER), request.phoneNumber())
                );
            }

            if(request.createdFrom() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get(Driver_.createdDate), request.createdFrom())
                );
            }

            if(request.createdTo() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get(Driver_.createdDate), request.createdTo()));
            }

            if(predicates.isEmpty()) {
                return cb.conjunction();
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
