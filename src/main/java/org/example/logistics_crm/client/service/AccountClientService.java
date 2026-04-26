package org.example.logistics_crm.client.service;

import org.example.logistics_crm.client.Client;
import org.example.logistics_crm.client.dto.ChangeClientEmailRequestDTO;
import org.example.logistics_crm.client.dto.ChangeClientPasswordRequestDTO;
import org.example.logistics_crm.client.dto.ChangeClientPhoneNumberDTO;
import org.example.logistics_crm.client.dto.ClientDetailsResponseDTO;

public interface AccountClientService {
    ClientDetailsResponseDTO changePassword(Long clientId, ChangeClientPasswordRequestDTO changeClientPasswordRequestDTO);

    ClientDetailsResponseDTO changeEmail(Long clientId, ChangeClientEmailRequestDTO changeClientEmailRequestDTO);

    ClientDetailsResponseDTO changePhoneNumber(Long clientId, ChangeClientPhoneNumberDTO changeClientPhoneNumberDTO);
}
