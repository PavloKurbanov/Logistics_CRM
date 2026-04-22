package org.example.logistics_crm.client.repository;

import org.example.logistics_crm.client.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    List<Client> findByFirstNameAndLastName(String firstName, String lastName);
}
