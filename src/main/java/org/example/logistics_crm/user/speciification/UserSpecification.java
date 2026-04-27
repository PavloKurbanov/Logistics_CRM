package org.example.logistics_crm.user.speciification;

import org.example.logistics_crm.user.User;
import org.example.logistics_crm.user.dto.request.UserSearchRequestDTO;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public final class UserSpecification {

    private UserSpecification() {
    }

    public static Specification<User> search(UserSearchRequestDTO request) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.firstName() != null && !request.firstName().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("firstName")),
                                "%" + request.firstName().toLowerCase() + "%"
                        ));
            }

            if (request.lastName() != null && !request.lastName().isBlank()) {
                predicates.add(
                        cb.like(cb.lower(root.get("lastName")), "%" + request.lastName().toLowerCase() + "%"
                        ));
            }

            if (request.email() != null && !request.email().isBlank()) {
                predicates.add(cb.equal(root.get("email"), request.email()));
            }

            if (request.phoneNumber() != null && !request.phoneNumber().isBlank()) {
                predicates.add(cb.equal(root.get("phoneNumber"), request.phoneNumber()));
            }

            if (request.userRole() != null) {
                predicates.add(cb.equal(root.get("userRole"), request.userRole()));
            }

            if (request.createdFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdDate"), request.createdFrom()));
            }

            if (request.createdTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdDate"), request.createdTo()));
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        });
    }
}
