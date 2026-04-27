package org.example.logistics_crm.service.user;

import org.example.logistics_crm.dto.user.request.ChangeUserEmailRequestDTO;
import org.example.logistics_crm.dto.user.request.ChangeUserPasswordRequestDTO;
import org.example.logistics_crm.dto.user.request.ChangeUserPhoneNumberDTO;
import org.example.logistics_crm.dto.user.response.UserDetailsResponseDTO;

public interface AccountUserService {
    UserDetailsResponseDTO changePassword(Long userId, ChangeUserPasswordRequestDTO changeUserPasswordRequestDTO);

    UserDetailsResponseDTO changeEmail(Long userId, ChangeUserEmailRequestDTO changeUserEmailRequestDTO);

    UserDetailsResponseDTO changePhoneNumber(Long userId, ChangeUserPhoneNumberDTO changeUserPhoneNumberDTO);
}
