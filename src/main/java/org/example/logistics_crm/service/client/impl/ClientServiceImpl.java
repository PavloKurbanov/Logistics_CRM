package org.example.logistics_crm.service.client.impl;

import lombok.extern.slf4j.Slf4j;
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
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
            throw new IllegalArgumentException("Client request can't be null");
        }

        log.debug("Attempting to create new client with email: {}, phoneNumber: {}"
                , createClientRequestDTO.email(), createClientRequestDTO.phoneNumber());

        if (clientRepository.existsByEmail(createClientRequestDTO.email())) {
            throw new IllegalArgumentException("Email " + createClientRequestDTO.email() + " already exists");
        }

        if (clientRepository.existsByPhoneNumber(createClientRequestDTO.phoneNumber())) {
            throw new IllegalArgumentException("Phone number " + createClientRequestDTO.phoneNumber() + " already exists");
        }

        String encodedPassword = passwordEncoder.encode(createClientRequestDTO.password());

        Client client = clientRepository.save(new Client(
                createClientRequestDTO.firstName(),
                createClientRequestDTO.lastName(),
                createClientRequestDTO.email(),
                createClientRequestDTO.phoneNumber(),
                encodedPassword
        ));
        log.info("Client with email: {}, phoneNumber: {} successfully created with id: {}"
                , createClientRequestDTO.email(), createClientRequestDTO.phoneNumber(), client.getId());
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
    @Transactional(readOnly = true)
    public ClientDetailsResponseDTO findById(Long clientId) {
        log.debug("Fetching client with id: {}", clientId);
        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("Client id must be greater than 0");
        }
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client with ID: " + clientId + " not found"));

        return mapToDetails(client);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientListResponseDTO> findAll(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide pagination parameters.");
        }
        log.debug("Fetching all clients page request:  {}", pageable);
        return mapToList(clientRepository.findAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientListResponseDTO> searchClient(ClientSearchRequestDTO requestDTO, Pageable pageable) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("Client search request can't be null");
        }

        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide pagination parameters.");
        }
        log.debug("Fetching all clients with criteria:  {}", requestDTO);
        Page<Client> all = clientRepository.findAll(ClientSpecification.search(requestDTO), pageable);
        return mapToList(all);
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
        log.debug("Attempting to delete client with id: {}", clientId);
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client with ID: " + clientId + " not found"));

        clientRepository.delete(client);
        log.info("Client with id: {} successfully deleted", clientId);
    }

    @Override
    @Transactional(readOnly = true)
    public Client getClientEntityById(Long clientId) {
        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("Client id must be greater than 0");
        }

        return clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client with ID: " + clientId + " not found"));
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
