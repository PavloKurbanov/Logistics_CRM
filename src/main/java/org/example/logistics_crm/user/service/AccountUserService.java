package org.example.logistics_crm.user.service;

import org.example.logistics_crm.user.User;
import org.example.logistics_crm.user.dto.ChangeUserEmailRequestDTO;
import org.example.logistics_crm.user.dto.ChangeUserPasswordRequestDTO;
import org.example.logistics_crm.user.dto.ChangeUserPhoneNumberDTO;

public interface AccountUserService {
    User changePassword(Long clientId, ChangeUserPasswordRequestDTO changeUserPasswordRequestDTO);

    User changeEmail(Long clientId, ChangeUserEmailRequestDTO changeUserEmailRequestDTO);

    User changePhoneNumber(Long clientId, ChangeUserPhoneNumberDTO changeUserPhoneNumberDTO);
}
