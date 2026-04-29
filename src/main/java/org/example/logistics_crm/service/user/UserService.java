package org.example.logistics_crm.service.user;

import org.example.logistics_crm.entity.user.User;
import org.example.logistics_crm.dto.user.request.CreateUserRequestDTO;
import org.example.logistics_crm.dto.user.request.UserSearchRequestDTO;
import org.example.logistics_crm.dto.user.response.UserDetailsResponseDTO;
import org.example.logistics_crm.dto.user.response.UserListResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDetailsResponseDTO  createUser(CreateUserRequestDTO createUserRequestDTO);

    void updateUser(User user);

    UserDetailsResponseDTO findById(Long userId);

    Page<UserListResponseDTO> findAll(Pageable pageable);

    void deleteUser(Long userId);

    Page<UserListResponseDTO> searchUser(UserSearchRequestDTO requestDTO, Pageable pageable);

    User getUserEntityById(Long userId);

    boolean existsByEmail(String email, Long userId);

    boolean existsByPhoneNumber(String phoneNumber, Long userId);
}
