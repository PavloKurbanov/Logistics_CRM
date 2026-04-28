package org.example.logistics_crm.controller;

import jakarta.validation.Valid;
import org.example.logistics_crm.dto.client.request.*;
import org.example.logistics_crm.dto.client.response.ClientDetailsResponseDTO;
import org.example.logistics_crm.dto.client.response.ClientListResponseDTO;
import org.example.logistics_crm.service.client.AccountClientService;
import org.example.logistics_crm.service.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    private final ClientService clientService;
    private final AccountClientService accountClientService;

    @Autowired
    public ClientController(ClientService clientService, AccountClientService accountClientService) {
        this.clientService = clientService;
        this.accountClientService = accountClientService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDetailsResponseDTO createClient(
            @Valid @RequestBody CreateClientRequestDTO createClientRequestDTO) {
        return clientService.createClient(createClientRequestDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDetailsResponseDTO findById(
            @PathVariable Long id) {
        return clientService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ClientListResponseDTO> findAll(Pageable pageable) {
        return clientService.findAll(pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id) {
        clientService.deleteClient(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<ClientListResponseDTO> searchClient(
            @ModelAttribute ClientSearchRequestDTO requestDTO, Pageable pageable) {
        return clientService.searchClient(requestDTO, pageable);
    }

    @PutMapping("/{id}/password")
    @ResponseStatus(HttpStatus.OK)
    public ClientDetailsResponseDTO changePassword(
            @PathVariable Long id, @Valid @RequestBody ChangeClientPasswordRequestDTO request) {
        return accountClientService.changePassword(id, request);
    }

    @PutMapping("/{id}/phoneNumber")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ClientDetailsResponseDTO changePhoneNumber(
            @PathVariable Long id, @Valid @RequestBody ChangeClientPhoneNumberDTO request) {
        return accountClientService.changePhoneNumber(id, request);
    }

    @PutMapping("/{id}/email")
    @ResponseStatus(HttpStatus.OK)
    public ClientDetailsResponseDTO changeEmail(
            @PathVariable Long id, @Valid @RequestBody ChangeClientEmailRequestDTO request) {
        return accountClientService.changeEmail(id, request);
    }
}
