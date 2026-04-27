package org.example.logistics_crm.user.service.impl;

import jakarta.transaction.Transactional;
import org.example.logistics_crm.user.User;
import org.example.logistics_crm.user.UserRole;
import org.example.logistics_crm.user.dto.request.CreateUserRequestDTO;
import org.example.logistics_crm.user.dto.request.UserSearchRequestDTO;
import org.example.logistics_crm.user.dto.response.UserDetailsResponseDTO;
import org.example.logistics_crm.user.dto.response.UserListResponseDTO;
import org.example.logistics_crm.user.repository.UserRepository;
import org.example.logistics_crm.user.service.UserService;
import org.example.logistics_crm.user.speciification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<UserListResponseDTO> findAll(UserSearchRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("User search request must not be null");
        }
        List<User> all = userRepository.findAll(UserSpecification.search(requestDTO));
        return mapToList(all);
    }

    @Override
    public List<UserListResponseDTO> findAll() {
        List<User> all = userRepository.findAll();

        return mapToList(all);
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

    private List<UserListResponseDTO> mapToList(List<User> users) {
        return users.stream()
                .map(user -> new UserListResponseDTO(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUserRole(),
                        user.getPhoneNumber(),
                        user.getEmail()
                ))
                .toList();
    }
}
