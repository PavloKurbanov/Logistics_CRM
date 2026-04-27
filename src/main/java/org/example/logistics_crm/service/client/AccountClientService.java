package org.example.logistics_crm.service.client;

import org.example.logistics_crm.dto.client.request.ChangeClientEmailRequestDTO;
import org.example.logistics_crm.dto.client.request.ChangeClientPasswordRequestDTO;
import org.example.logistics_crm.dto.client.request.ChangeClientPhoneNumberDTO;
import org.example.logistics_crm.dto.client.response.ClientDetailsResponseDTO;

public interface AccountClientService {
    ClientDetailsResponseDTO changePassword(Long clientId, ChangeClientPasswordRequestDTO changeClientPasswordRequestDTO);

    ClientDetailsResponseDTO changeEmail(Long clientId, ChangeClientEmailRequestDTO changeClientEmailRequestDTO);

    ClientDetailsResponseDTO changePhoneNumber(Long clientId, ChangeClientPhoneNumberDTO changeClientPhoneNumberDTO);
}
