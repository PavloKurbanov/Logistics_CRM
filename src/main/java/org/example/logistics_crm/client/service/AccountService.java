package org.example.logistics_crm.client.service;

import org.example.logistics_crm.client.Client;
import org.example.logistics_crm.client.dto.ChangeEmailRequestDTO;
import org.example.logistics_crm.client.dto.ChangePasswordRequestDTO;
import org.example.logistics_crm.client.dto.ChangePhoneNumberDTO;

public interface AccountService {
    Client changePassword(Long clientId, ChangePasswordRequestDTO changePasswordRequestDTO);

    Client changeEmail(Long clientId, ChangeEmailRequestDTO changeEmailRequestDTO);

    Client changePhoneNumber(Long clientId, ChangePhoneNumberDTO changePhoneNumberDTO);
}
