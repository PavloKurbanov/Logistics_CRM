package org.example.logistics_crm.client.repository;

import org.example.logistics_crm.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByFirstName(String firstName);

    List<Client> findByLastName(String lastName);

    Optional<Client> findByEmail(String email);

    Optional<Client> findByPhone(String phone);

    List<Client> findByFirstNameAndLastName(String firstName, String lastName);
}
