package org.example.logistics_crm.service.client.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.logistics_crm.entity.client.Client;
import org.example.logistics_crm.dto.client.request.ChangeClientEmailRequestDTO;
import org.example.logistics_crm.dto.client.request.ChangeClientPasswordRequestDTO;
import org.example.logistics_crm.dto.client.request.ChangeClientPhoneNumberDTO;
import org.example.logistics_crm.dto.client.response.ClientDetailsResponseDTO;
import org.example.logistics_crm.security.SecurityClient;
import org.example.logistics_crm.service.client.AccountClientService;
import org.example.logistics_crm.service.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
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
        log.debug("Attempting to change password for client with id: {}", clientId);
        ensureTargetClientIsCurrentUser(clientId);
        Client client = validateClientAndPassword(clientId, changeClientPasswordRequestDTO.oldPassword());

        if (!changeClientPasswordRequestDTO.newPassword().equals(changeClientPasswordRequestDTO.confirmNewPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (passwordEncoder.matches(changeClientPasswordRequestDTO.newPassword(), client.getPassword())) {
            throw new IllegalArgumentException("New password must be different from old password");
        }

        String encodedPassword = passwordEncoder.encode(changeClientPasswordRequestDTO.newPassword());

        client.setPassword(encodedPassword);
        log.info("Changing password for client with id: {} successful", clientId);
        return mapToDetails(client);
    }

    @Override
    @Transactional
    public ClientDetailsResponseDTO changeEmail(Long clientId, ChangeClientEmailRequestDTO changeClientEmailRequestDTO) {
        ensureTargetClientIsCurrentUser(clientId);
        log.debug("Attempting to change email for client with id: {}", clientId);
        Client client = validateClientAndPassword(clientId, changeClientEmailRequestDTO.currentPassword());

        if (client.getEmail().equals(changeClientEmailRequestDTO.newEmail())) {
            throw new IllegalArgumentException("New email is the same as the old email");
        }

        if (clientService.existsByEmailAndIdNot(changeClientEmailRequestDTO.newEmail(), clientId)) {
            throw new IllegalArgumentException("Email " + changeClientEmailRequestDTO.newEmail() + " already exists");
        }

        client.setEmail(changeClientEmailRequestDTO.newEmail());
        log.info("Changing email for client with id: {} successful", clientId);
        return mapToDetails(client);
    }

    @Override
    @Transactional
    public ClientDetailsResponseDTO changePhoneNumber(Long clientId, ChangeClientPhoneNumberDTO changeClientPhoneNumberDTO) {
        ensureTargetClientIsCurrentUser(clientId);
        log.debug("Attempting to change phone number for client with id: {}", clientId);
        Client client = validateClientAndPassword(clientId, changeClientPhoneNumberDTO.currentPassword());

        if (client.getPhoneNumber().equals(changeClientPhoneNumberDTO.newPhoneNumber())) {
            throw new IllegalArgumentException("New phone number is the same as the old phone number");
        }

        if (clientService.existsByPhoneNumberAndIdNot(changeClientPhoneNumberDTO.newPhoneNumber(), clientId)) {
            throw new IllegalArgumentException("Phone number " + changeClientPhoneNumberDTO.newPhoneNumber() + " already exists");
        }
        client.setPhoneNumber(changeClientPhoneNumberDTO.newPhoneNumber());
        log.info("Changing phone number for client with id: {} successful", clientId);
        return mapToDetails(client);
    }

    private void ensureTargetClientIsCurrentUser(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null){
            throw new IllegalArgumentException("Current user is not authenticated");
        }

        Object principal = authentication.getPrincipal();

        if(principal instanceof SecurityClient securityClient){
            Long clientId = securityClient.getId();
            if(!clientId.equals(id)){
                throw new AccessDeniedException("Invalid user id");
            }
        } else {
            throw new IllegalStateException("Invalid user type in session");
        }
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
