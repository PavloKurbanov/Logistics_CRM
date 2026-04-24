package org.example.logistics_crm.client.service.impl;

import jakarta.transaction.Transactional;
import org.example.logistics_crm.client.Client;
import org.example.logistics_crm.client.dto.ChangeEmailRequestDTO;
import org.example.logistics_crm.client.dto.ChangePasswordRequestDTO;
import org.example.logistics_crm.client.dto.ChangePhoneNumberDTO;
import org.example.logistics_crm.client.service.AccountService;
import org.example.logistics_crm.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(ClientService clientService, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Client changePassword(Long clientId, ChangePasswordRequestDTO changePasswordRequestDTO) {
        Client client = validateClientAndPassword(clientId, changePasswordRequestDTO.oldPassword());

        if (!changePasswordRequestDTO.newPassword().equals(changePasswordRequestDTO.confirmNewPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (passwordEncoder.matches(changePasswordRequestDTO.newPassword(), client.getPassword())) {
            throw new IllegalArgumentException("New password must be different from old password");
        }

        if (client.getPassword().equals(changePasswordRequestDTO.newPassword())) {
            throw new IllegalArgumentException("New password is the same as the old password");
        }

        String encodedPassword = passwordEncoder.encode(changePasswordRequestDTO.newPassword());
        client.setPassword(encodedPassword);
        return clientService.update(client);
    }

    @Override
    @Transactional
    public Client changeEmail(Long clientId, ChangeEmailRequestDTO changeEmailRequestDTO) {
        Client client = validateClientAndPassword(clientId, changeEmailRequestDTO.currentPassword());

        if (client.getEmail().equals(changeEmailRequestDTO.newEmail())) {
            throw new IllegalArgumentException("New email is the same as the old email");
        }

        Optional<Client> existingClient = clientService.findByEmail(changeEmailRequestDTO.newEmail());

        if (existingClient.isPresent() && !existingClient.get().getId().equals(client.getId())) {
            throw new IllegalArgumentException("Email already exists");
        }

        client.setEmail(changeEmailRequestDTO.newEmail());
        return clientService.update(client);
    }

    @Override
    @Transactional
    public Client changePhoneNumber(Long clientId, ChangePhoneNumberDTO changePhoneNumberDTO) {
        Client client = validateClientAndPassword(clientId, changePhoneNumberDTO.currentPassword());

        if (client.getPhoneNumber().equals(changePhoneNumberDTO.newPhoneNumber())) {
            throw new IllegalArgumentException("New phone number is the same as the old phone number");
        }

        Optional<Client> existingClient = clientService.findByPhone(changePhoneNumberDTO.newPhoneNumber());

        if (existingClient.isPresent() && !existingClient.get().getId().equals(client.getId())) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        client.setPhoneNumber(changePhoneNumberDTO.newPhoneNumber());
        return clientService.update(client);
    }

    private Client validateClientAndPassword(Long clientId, String currentPassword) {
        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("Client id must be greater than 0");
        }

        Client client = clientService.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client with id " + clientId + " not found"));

        if (!passwordEncoder.matches(currentPassword, client.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        return client;
    }
}
