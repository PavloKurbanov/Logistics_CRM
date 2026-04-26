package org.example.logistics_crm.user.repository;

import org.example.logistics_crm.user.User;
import org.example.logistics_crm.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findByFirstNameAndLastName(String firstName, String lastName);

    List<User> findByUserRole(UserRole role);
}
