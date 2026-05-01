package org.example.logistics_crm.service.client.impl;

import jakarta.transaction.Transactional;
import org.example.logistics_crm.dto.client.request.ClientSearchRequestDTO;
import org.example.logistics_crm.dto.client.request.CreateClientRequestDTO;
import org.example.logistics_crm.dto.client.response.ClientDetailsResponseDTO;
import org.example.logistics_crm.dto.client.response.ClientListResponseDTO;
import org.example.logistics_crm.entity.client.Client;
import org.example.logistics_crm.repository.ClientRepository;
import org.example.logistics_crm.service.client.ClientService;
import org.example.logistics_crm.specification.ClientSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public ClientDetailsResponseDTO createClient(CreateClientRequestDTO createClientRequestDTO) {
        if (createClientRequestDTO == null) {
            throw new IllegalArgumentException("Client can't be null");
        }

        if (clientRepository.existsByEmail(createClientRequestDTO.email())) {
            throw new IllegalArgumentException("Email  already exists");
        }

        if (clientRepository.existsByPhoneNumber(createClientRequestDTO.phoneNumber())) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        String encodedPassword = passwordEncoder.encode(createClientRequestDTO.password());

        Client client = clientRepository.save(new Client(
                createClientRequestDTO.firstName(),
                createClientRequestDTO.lastName(),
                createClientRequestDTO.email(),
                createClientRequestDTO.phoneNumber(),
                encodedPassword
        ));

        return mapToDetails(client);
    }

    @Override
    @Transactional
    public void update(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client can't be null");
        }

        clientRepository.save(client);
    }

    @Override
    public ClientDetailsResponseDTO findById(Long clientId) {
        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("Client id must be greater than 0");
        }
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        return mapToDetails(client);
    }

    @Override
    public Page<ClientListResponseDTO> findAll(Pageable pageable) {
        if(pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide pagination parameters.");
        }

        return mapToList(clientRepository.findAll(pageable));
    }

    @Override
    public Page<ClientListResponseDTO> searchClient(ClientSearchRequestDTO requestDTO, Pageable pageable) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("Client search request can't be null");
        }

        if(pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide pagination parameters.");
        }

        Page<Client> all = clientRepository.findAll(ClientSpecification.search(requestDTO), pageable);
        return mapToList(all);
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, Long id) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email can't be null");
        }
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id can't be null");
        }
        return clientRepository.existsByEmailAndIdNot(email, id);
    }

    @Override
    public boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number can't be null");
        }
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id can't be null");
        }
        return clientRepository.existsByPhoneNumberAndIdNot(phoneNumber, id);
    }

    @Override
    @Transactional
    public void deleteClient(Long clientId) {
        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("Client id must be greater than 0");
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        clientRepository.delete(client);
    }

    @Override
    public Client getClientEntityById(Long clientId) {
        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("Client id must be greater than 0");
        }

        return clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
    }

    private Page<ClientListResponseDTO> mapToList(Page<Client> clientPage) {
        return clientPage
                .map(client -> new ClientListResponseDTO(
                        client.getId(),
                        client.getFirstName(),
                        client.getLastName(),
                        client.getEmail(),
                        client.getPhoneNumber()
                ));
    }

    private ClientDetailsResponseDTO mapToDetails(Client client) {
        return new ClientDetailsResponseDTO(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getPhoneNumber(),
                client.getCreatedDate(),
                client.getUpdatedDate()
        );
    }
}
