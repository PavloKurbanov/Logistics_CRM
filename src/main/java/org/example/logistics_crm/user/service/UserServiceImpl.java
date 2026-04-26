package org.example.logistics_crm.user.service;

import org.example.logistics_crm.user.User;
import org.example.logistics_crm.user.dto.CreateUserRequestDTO;
import org.example.logistics_crm.user.dto.UserDetailsResponseDTO;
import org.example.logistics_crm.user.dto.UserListResponseDTO;
import org.example.logistics_crm.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public User createUser(CreateUserRequestDTO createUserRequestDTO) {
        if (createUserRequestDTO == null) {
            throw new IllegalArgumentException("User can't be null");
        }

        if (userRepository.findByEmail(createUserRequestDTO.email()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (userRepository.findByPhoneNumber(createUserRequestDTO.phoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        String encodedPassword = passwordEncoder.encode(createUserRequestDTO.password());

        User user = new User(
                createUserRequestDTO.firstName(),
                createUserRequestDTO.lastName(),
                createUserRequestDTO.email(),
                createUserRequestDTO.phoneNumber(),
                encodedPassword
        );

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User can't be null");
        }

        return userRepository.save(user);
    }

    @Override
    public UserDetailsResponseDTO findById(Long clientId) {

        User user = userRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

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

    @Override
    public List<UserListResponseDTO> findByFirstName(String firstName) {
        return List.of();
    }

    @Override
    public List<UserListResponseDTO> findByLastName(String lastName) {
        return List.of();
    }

    @Override
    public Optional<UserDetailsResponseDTO> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDetailsResponseDTO> findByPhoneNumber(String phone) {
        return Optional.empty();
    }

    @Override
    public List<UserListResponseDTO> findAll() {
        return List.of();
    }

    @Override
    public List<UserListResponseDTO> findByFirstNameAndLastName(String firstName, String lastName) {
        return List.of();
    }
}
