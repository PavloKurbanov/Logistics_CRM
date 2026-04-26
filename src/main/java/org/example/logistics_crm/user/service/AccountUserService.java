package org.example.logistics_crm.user.service;

import org.example.logistics_crm.user.dto.ChangeUserEmailRequestDTO;
import org.example.logistics_crm.user.dto.ChangeUserPasswordRequestDTO;
import org.example.logistics_crm.user.dto.ChangeUserPhoneNumberDTO;
import org.example.logistics_crm.user.dto.UserDetailsResponseDTO;

public interface AccountUserService {
    UserDetailsResponseDTO changePassword(Long userId, ChangeUserPasswordRequestDTO changeUserPasswordRequestDTO);

    UserDetailsResponseDTO changeEmail(Long userId, ChangeUserEmailRequestDTO changeUserEmailRequestDTO);

    UserDetailsResponseDTO changePhoneNumber(Long userId, ChangeUserPhoneNumberDTO changeUserPhoneNumberDTO);
}
