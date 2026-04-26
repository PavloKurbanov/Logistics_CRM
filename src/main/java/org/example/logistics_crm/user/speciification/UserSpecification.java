package org.example.logistics_crm.user.speciification;

import org.example.logistics_crm.user.User;
import org.example.logistics_crm.user.dto.request.UserSearchRequestDTO;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public final class UserSpecification {

    public static Specification<User> getUserSpecification(UserSearchRequestDTO request) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.firstName() != null && !request.firstName().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("firstName")),
                                "%" + request.firstName().toLowerCase() + "%"
                        );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
