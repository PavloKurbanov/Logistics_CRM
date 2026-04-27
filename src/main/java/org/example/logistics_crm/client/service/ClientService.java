package org.example.logistics_crm.client.service;

import org.example.logistics_crm.client.Client;
import org.example.logistics_crm.client.dto.request.ClientSearchRequestDTO;
import org.example.logistics_crm.client.dto.response.ClientDetailsResponseDTO;
import org.example.logistics_crm.client.dto.response.ClientListResponseDTO;
import org.example.logistics_crm.client.dto.request.CreateClientRequestDTO;

import java.util.List;

public interface ClientService {
    ClientDetailsResponseDTO createClient(CreateClientRequestDTO client);

    void update(Client client);

    ClientDetailsResponseDTO findById(Long clientId);

    List<ClientListResponseDTO> findAll();

    void deleteClient(Long clientId);

    List<ClientListResponseDTO> searchClient(ClientSearchRequestDTO requestDTO);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

    Client getClientEntityById(Long clientId);
}
