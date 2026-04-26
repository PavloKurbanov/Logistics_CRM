package org.example.logistics_crm.user.service.impl;

import jakarta.transaction.Transactional;
import org.example.logistics_crm.user.User;
import org.example.logistics_crm.user.UserRole;
import org.example.logistics_crm.user.dto.CreateUserRequestDTO;
import org.example.logistics_crm.user.dto.UserDetailsResponseDTO;
import org.example.logistics_crm.user.dto.UserListResponseDTO;
import org.example.logistics_crm.user.repository.UserRepository;
import org.example.logistics_crm.user.service.UserService;
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
    public User createUser(CreateUserRequestDTO createUserRequestDTO) {
        if (createUserRequestDTO == null) {
            throw new IllegalArgumentException("Create user request must not be null");
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
    public List<UserListResponseDTO> findByFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name can't be null");
        }

        List<User> byFirstName = userRepository.findByFirstName(firstName);

        return mapToList(byFirstName);
    }

    @Override
    public List<UserListResponseDTO> findByLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name can't be null");
        }

        List<User> byLastName = userRepository.findByLastName(lastName);

        return mapToList(byLastName);
    }

    @Override
    public UserDetailsResponseDTO findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email can't be null");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return mapToDetails(user);
    }

    @Override
    public UserDetailsResponseDTO findByPhoneNumber(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone number can't be null");
        }

        User user = userRepository.findByPhoneNumber(phone)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return mapToDetails(user);
    }

    @Override
    public List<UserListResponseDTO> findAll() {
        List<User> all = userRepository.findAll();

        return mapToList(all);
    }

    @Override
    public List<UserListResponseDTO> searchUsers(String firstName, String lastName) {
        if (firstName == null || lastName == null || firstName.isBlank() || lastName.isBlank()) {
            throw new IllegalArgumentException("First name and last name can't be null");
        }

        List<User> byFirstNameAndLastName = userRepository.findByFirstNameAndLastName(firstName, lastName);

        return mapToList(byFirstNameAndLastName);
    }

    @Override
    public List<UserListResponseDTO> findByUserRole(UserRole userRole) {
        if (userRole == null) {
            throw new IllegalArgumentException("User role can't be null");
        }
        List<User> byUserRole = userRepository.findByUserRole(userRole);
        return mapToList(byUserRole);
    }

    @Override
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
                        user.getUserRole()
                ))
                .toList();
    }
}
