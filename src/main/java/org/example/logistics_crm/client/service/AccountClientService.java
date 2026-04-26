package org.example.logistics_crm.client.service;

import org.example.logistics_crm.client.Client;
import org.example.logistics_crm.client.dto.ChangeClientEmailRequestDTO;
import org.example.logistics_crm.client.dto.ChangeClientPasswordRequestDTO;
import org.example.logistics_crm.client.dto.ChangeClientPhoneNumberDTO;

public interface AccountClientService {
    Client changePassword(Long clientId, ChangeClientPasswordRequestDTO changeClientPasswordRequestDTO);

    Client changeEmail(Long clientId, ChangeClientEmailRequestDTO changeClientEmailRequestDTO);

    Client changePhoneNumber(Long clientId, ChangeClientPhoneNumberDTO changeClientPhoneNumberDTO);
}
