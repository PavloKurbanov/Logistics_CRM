package org.example.logistics_crm.service.user.impl;

import jakarta.transaction.Transactional;
import org.example.logistics_crm.dto.user.request.CreateUserRequestDTO;
import org.example.logistics_crm.dto.user.request.UserSearchRequestDTO;
import org.example.logistics_crm.dto.user.response.UserDetailsResponseDTO;
import org.example.logistics_crm.dto.user.response.UserListResponseDTO;
import org.example.logistics_crm.entity.user.User;
import org.example.logistics_crm.repository.UserRepository;
import org.example.logistics_crm.service.user.UserService;
import org.example.logistics_crm.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetailsResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO) {
        if (createUserRequestDTO == null) {
            throw new IllegalArgumentException("Create user request must not be null");
        }

        if (userRepository.existsByEmail(createUserRequestDTO.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (userRepository.existsByPhoneNumber(createUserRequestDTO.phoneNumber())) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        String encodedPassword = passwordEncoder.encode(createUserRequestDTO.password());

        User user = userRepository.save(new User(
                createUserRequestDTO.firstName(),
                createUserRequestDTO.lastName(),
                createUserRequestDTO.email(),
                createUserRequestDTO.phoneNumber(),
                encodedPassword)
        );

        return mapToDetails(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        userRepository.save(user);
    }

    @Override
    public UserDetailsResponseDTO findById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return mapToDetails(user);
    }

    @Override
    public Page<UserListResponseDTO> searchUser(UserSearchRequestDTO requestDTO, Pageable pageable) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("User search request must not be null");
        }

        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide pagination parameters.");
        }

        Page<User> all = userRepository.findAll(UserSpecification.search(requestDTO), pageable);
        return mapToList(all);
    }

    @Override
    public Page<UserListResponseDTO> findAll(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide pagination parameters.");
        }

        return mapToList(userRepository.findAll(pageable));
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id must be greater than 0");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userRepository.delete(user);
    }

    @Override
    public User getUserEntityById(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id must be greater than 0");
        }

        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public boolean existsByEmail(String email, Long userId) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email can't be null");
        }
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id must be greater than 0");
        }
        return userRepository.existsByEmailAndIdNot(email, userId);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber, Long userId) {
        return userRepository.existsByPhoneNumberAndIdNot(phoneNumber, userId);
    }

    private UserDetailsResponseDTO mapToDetails(User user) {
        return new UserDetailsResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getUserRole(),
                user.getCreatedDate(),
                user.getUpdatedDate()
        );
    }

    private Page<UserListResponseDTO> mapToList(Page<User> users) {
        return users
                .map(user -> new UserListResponseDTO(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUserRole(),
                        user.getPhoneNumber(),
                        user.getEmail()
                ));
    }
}
