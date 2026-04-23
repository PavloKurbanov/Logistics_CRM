package org.example.logistics_crm.client.service.impl;

import jakarta.transaction.Transactional;
import org.example.logistics_crm.client.Client;
import org.example.logistics_crm.client.dto.CreateClientRequestDTO;
import org.example.logistics_crm.client.repository.ClientRepository;
import org.example.logistics_crm.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Client createClient(CreateClientRequestDTO client) {
        if (client == null) {
            throw new IllegalArgumentException("Client can't be null");
        }

        if (clientRepository.findByEmail(client.email()).isPresent()) {
            throw new IllegalArgumentException("Email  already exists");
        }

        if (clientRepository.findByPhone(client.phoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        String encode = passwordEncoder.encode(client.password()); // matches() щоб перевірити пароль на правильність

        return clientRepository.save(new Client(
                client.firstName(),
                client.lastName(),
                client.email(),
                client.phoneNumber(),
                encode
        ));
    }

    @Override
    public Optional<Client> findById(Long clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException("Client id can't be null");
        }
        return clientRepository.findById(clientId);
    }

    @Override
    public List<Client> findByFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name can't be null");
        }
        return clientRepository.findByFirstName(firstName);
    }

    @Override
    public List<Client> findByLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name can't be null");
        }
        return clientRepository.findByLastName(lastName);
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email can't be null");
        }
        return clientRepository.findByEmail(email);
    }

    @Override
    public Optional<Client> findByPhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone can't be null");
        }
        return clientRepository.findByPhone(phone);
    }

    @Override
    public List<Client> findByFirstNameAndLastName(String firstName, String lastName) {
        if (firstName == null || lastName == null || firstName.isBlank() || lastName.isBlank()) {
            throw new IllegalArgumentException("First name and last name can't be null");
        }
        return clientRepository.findByFirstNameAndLastName(firstName, lastName);
    }
}
