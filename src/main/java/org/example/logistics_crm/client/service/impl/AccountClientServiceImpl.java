package org.example.logistics_crm.client.service.impl;

import jakarta.transaction.Transactional;
import org.example.logistics_crm.client.Client;
import org.example.logistics_crm.client.dto.request.ChangeClientEmailRequestDTO;
import org.example.logistics_crm.client.dto.request.ChangeClientPasswordRequestDTO;
import org.example.logistics_crm.client.dto.request.ChangeClientPhoneNumberDTO;
import org.example.logistics_crm.client.dto.response.ClientDetailsResponseDTO;
import org.example.logistics_crm.client.service.AccountClientService;
import org.example.logistics_crm.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountClientServiceImpl implements AccountClientService {

    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountClientServiceImpl(ClientService clientService, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public ClientDetailsResponseDTO changePassword(Long clientId, ChangeClientPasswordRequestDTO changeClientPasswordRequestDTO) {
        Client client = validateClientAndPassword(clientId, changeClientPasswordRequestDTO.oldPassword());

        if (!changeClientPasswordRequestDTO.newPassword().equals(changeClientPasswordRequestDTO.confirmNewPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (passwordEncoder.matches(changeClientPasswordRequestDTO.newPassword(), client.getPassword())) {
            throw new IllegalArgumentException("New password must be different from old password");
        }

        String encodedPassword = passwordEncoder.encode(changeClientPasswordRequestDTO.newPassword());

        client.setPassword(encodedPassword);
        clientService.update(client);
        return mapToDetails(client);
    }

    @Override
    @Transactional
    public ClientDetailsResponseDTO changeEmail(Long clientId, ChangeClientEmailRequestDTO changeClientEmailRequestDTO) {
        Client client = validateClientAndPassword(clientId, changeClientEmailRequestDTO.currentPassword());

        if (client.getEmail().equals(changeClientEmailRequestDTO.newEmail())) {
            throw new IllegalArgumentException("New email is the same as the old email");
        }

        if(clientService.existsByEmailAndIdNot(changeClientEmailRequestDTO.newEmail(), clientId)) {
            throw new IllegalArgumentException("Email already exists");
        }

        client.setEmail(changeClientEmailRequestDTO.newEmail());
        clientService.update(client);
        return mapToDetails(client);
    }

    @Override
    @Transactional
    public ClientDetailsResponseDTO changePhoneNumber(Long clientId, ChangeClientPhoneNumberDTO changeClientPhoneNumberDTO) {
        Client client = validateClientAndPassword(clientId, changeClientPhoneNumberDTO.currentPassword());

        if (client.getPhoneNumber().equals(changeClientPhoneNumberDTO.newPhoneNumber())) {
            throw new IllegalArgumentException("New phone number is the same as the old phone number");
        }

        if(clientService.existsByPhoneNumberAndIdNot(changeClientPhoneNumberDTO.newPhoneNumber(), clientId)) {
            throw new IllegalArgumentException("Phone number already exists");
        }
        client.setPhoneNumber(changeClientPhoneNumberDTO.newPhoneNumber());
        clientService.update(client);
        return mapToDetails(client);
    }

    private Client validateClientAndPassword(Long clientId, String currentPassword) {
        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("Client id must be greater than 0");
        }

        Client client = clientService.getClientEntityById(clientId);

        if (!passwordEncoder.matches(currentPassword, client.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        return client;
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
