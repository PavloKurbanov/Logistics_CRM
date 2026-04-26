package org.example.logistics_crm.user.service;

import org.example.logistics_crm.user.dto.request.ChangeUserEmailRequestDTO;
import org.example.logistics_crm.user.dto.request.ChangeUserPasswordRequestDTO;
import org.example.logistics_crm.user.dto.request.ChangeUserPhoneNumberDTO;
import org.example.logistics_crm.user.dto.response.UserDetailsResponseDTO;

public interface AccountUserService {
    UserDetailsResponseDTO changePassword(Long userId, ChangeUserPasswordRequestDTO changeUserPasswordRequestDTO);

    UserDetailsResponseDTO changeEmail(Long userId, ChangeUserEmailRequestDTO changeUserEmailRequestDTO);

    UserDetailsResponseDTO changePhoneNumber(Long userId, ChangeUserPhoneNumberDTO changeUserPhoneNumberDTO);
}
