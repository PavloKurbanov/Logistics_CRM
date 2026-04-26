package org.example.logistics_crm.user.service;

import org.example.logistics_crm.user.User;
import org.example.logistics_crm.user.dto.CreateUserRequestDTO;
import org.example.logistics_crm.user.dto.UpdateUserRequestDTO;
import org.example.logistics_crm.user.dto.UserDetailsResponseDTO;
import org.example.logistics_crm.user.dto.UserListResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(CreateUserRequestDTO createUserRequestDTO);

    //User updateUser(Long Id, UpdateUserRequestDTO updateUserRequestDTO);

    UserDetailsResponseDTO findById(Long clientId);

    List<UserListResponseDTO> findByFirstName(String firstName);

    List<UserListResponseDTO> findByLastName(String lastName);

    UserDetailsResponseDTO findByEmail(String email);

    UserDetailsResponseDTO findByPhoneNumber(String phone);

    List<UserListResponseDTO> findAll();

    List<UserListResponseDTO> findByFirstNameAndLastName(String firstName, String lastName);
}
