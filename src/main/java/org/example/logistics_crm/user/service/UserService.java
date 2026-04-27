package org.example.logistics_crm.user.service;

import org.example.logistics_crm.user.User;
import org.example.logistics_crm.user.UserRole;
import org.example.logistics_crm.user.dto.request.CreateUserRequestDTO;
import org.example.logistics_crm.user.dto.request.UserSearchRequestDTO;
import org.example.logistics_crm.user.dto.response.UserDetailsResponseDTO;
import org.example.logistics_crm.user.dto.response.UserListResponseDTO;

import java.util.List;
import java.util.ListResourceBundle;

public interface UserService {
    UserDetailsResponseDTO  createUser(CreateUserRequestDTO createUserRequestDTO);

    void updateUser(User user);

    UserDetailsResponseDTO findById(Long userId);

    List<UserListResponseDTO> findAll();

    void deleteUser(Long userId);

    List<UserListResponseDTO> findAll(UserSearchRequestDTO requestDTO);

    User getUserEntityById(Long userId);

    boolean existsByEmail(String email, Long userId);

    boolean existsByPhoneNumber(String phoneNumber, Long userId);
}
