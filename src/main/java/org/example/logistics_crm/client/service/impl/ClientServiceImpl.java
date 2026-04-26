package org.example.logistics_crm.client.service.impl;

import jakarta.transaction.Transactional;
import org.example.logistics_crm.client.Client;
import org.example.logistics_crm.client.dto.response.ClientDetailsResponseDTO;
import org.example.logistics_crm.client.dto.response.ClientListResponseDTO;
import org.example.logistics_crm.client.dto.request.CreateClientRequestDTO;
import org.example.logistics_crm.client.repository.ClientRepository;
import org.example.logistics_crm.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

        if (clientRepository.findByEmail(createClientRequestDTO.email()).isPresent()) {
            throw new IllegalArgumentException("Email  already exists");
        }

        if (clientRepository.findByPhoneNumber(createClientRequestDTO.phoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        String encodedPassword = passwordEncoder.encode(createClientRequestDTO.password()); // matches() щоб перевірити пароль на правильність

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
    public List<ClientListResponseDTO> findByFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name can't be null");
        }
        return mapToList(clientRepository.findByFirstName(firstName));
    }

    @Override
    public List<ClientListResponseDTO> findByLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name can't be null");
        }
        return mapToList(clientRepository.findByLastName(lastName));
    }

    @Override
    public List<ClientListResponseDTO> findAll() {
        return mapToList(clientRepository.findAll());
    }

    @Override
    public ClientDetailsResponseDTO findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email can't be null");
        }
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        return mapToDetails(client);
    }

    @Override
    public ClientDetailsResponseDTO findByPhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone can't be null");
        }
        Client client = clientRepository.findByPhoneNumber(phone)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        return mapToDetails(client);
    }

    @Override
    public List<ClientListResponseDTO> searchClients(String firstName, String lastName) {
        if (firstName == null || lastName == null || firstName.isBlank() || lastName.isBlank()) {
            throw new IllegalArgumentException("First name and last name can't be null");
        }
        return mapToList(clientRepository.findByFirstNameAndLastName(firstName, lastName));
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
        if(clientId == null || clientId <= 0) {
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

    private List<ClientListResponseDTO> mapToList(List<Client> clients) {
        return clients.stream()
                .map(client -> new ClientListResponseDTO(
                        client.getId(),
                        client.getFirstName(),
                        client.getLastName())
                ).toList();
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
