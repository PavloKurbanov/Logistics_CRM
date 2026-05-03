package org.example.logistics_crm.specification;

import org.example.logistics_crm.entity.user.User;
import org.example.logistics_crm.dto.user.request.UserSearchRequestDTO;
import org.example.logistics_crm.entity.user.User_;
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
                                cb.lower(root.get(User_.firstName)),
                                request.firstName().toLowerCase() + "%"
                        ));
            }

            if (request.lastName() != null && !request.lastName().isBlank()) {
                predicates.add(
                        cb.like(cb.lower(root.get(User_.lastName)),
                                request.lastName().toLowerCase() + "%"
                        ));
            }

            if (request.email() != null && !request.email().isBlank()) {
                predicates.add(cb.equal(root.get(User_.email), request.email()));
            }

            if (request.phoneNumber() != null && !request.phoneNumber().isBlank()) {
                predicates.add(cb.equal(root.get(User_.phoneNumber), request.phoneNumber()));
            }

            if (request.userRole() != null) {
                predicates.add(cb.equal(root.get(User_.userRole), request.userRole()));
            }

            if (request.createdFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get(User_.createdDate), request.createdFrom()));
            }

            if (request.createdTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get(User_.createdDate), request.createdTo()));
            }

            if (predicates.isEmpty()) {
                cb.conjunction();
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        });
    }
}
