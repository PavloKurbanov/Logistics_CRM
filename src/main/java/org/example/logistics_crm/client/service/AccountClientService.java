package org.example.logistics_crm.client.service;

import org.example.logistics_crm.client.dto.request.ChangeClientEmailRequestDTO;
import org.example.logistics_crm.client.dto.request.ChangeClientPasswordRequestDTO;
import org.example.logistics_crm.client.dto.request.ChangeClientPhoneNumberDTO;
import org.example.logistics_crm.client.dto.response.ClientDetailsResponseDTO;

public interface AccountClientService {
    ClientDetailsResponseDTO changePassword(Long clientId, ChangeClientPasswordRequestDTO changeClientPasswordRequestDTO);

    ClientDetailsResponseDTO changeEmail(Long clientId, ChangeClientEmailRequestDTO changeClientEmailRequestDTO);

    ClientDetailsResponseDTO changePhoneNumber(Long clientId, ChangeClientPhoneNumberDTO changeClientPhoneNumberDTO);
}
