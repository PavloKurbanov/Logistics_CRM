package org.example.logistics_crm.user.service;

import org.example.logistics_crm.user.User;
import org.example.logistics_crm.user.UserRole;
import org.example.logistics_crm.user.dto.CreateUserRequestDTO;
import org.example.logistics_crm.user.dto.UserDetailsResponseDTO;
import org.example.logistics_crm.user.dto.UserListResponseDTO;

import java.util.List;

public interface UserService {
    UserDetailsResponseDTO  createUser(CreateUserRequestDTO createUserRequestDTO);

    void updateUser(User user);

    UserDetailsResponseDTO findById(Long userId);

    List<UserListResponseDTO> findByFirstName(String firstName);

    List<UserListResponseDTO> findByLastName(String lastName);

    UserDetailsResponseDTO findByEmail(String email);

    UserDetailsResponseDTO findByPhoneNumber(String phone);

    List<UserListResponseDTO> findAll();

    List<UserListResponseDTO> searchUsers(String firstName, String lastName);

    List<UserListResponseDTO> findByUserRole(UserRole userRole);

    void deleteUser(Long userId);

    User getUserEntityById(Long userId);

    boolean existsByEmail(String email, Long userId);

    boolean existsByPhoneNumber(String phoneNumber, Long userId);
}
