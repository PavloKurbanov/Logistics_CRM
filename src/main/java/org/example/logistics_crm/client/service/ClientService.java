package org.example.logistics_crm.client.service;

import org.example.logistics_crm.client.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client createClient(Client client);

    Optional<Client> findById(Long clientId);

    List<Client> findByFirstName(String firstName);

    List<Client> findByLastName(String lastName);

    Optional<Client> findByEmail(String email);

    Optional<Client> findByPhone(String phone);

    Optional<Client> findByFirstNameAndLastName(String firstName, String lastName);
}
