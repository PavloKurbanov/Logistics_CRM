package org.example.logistics_crm.user.service.impl;

import jakarta.transaction.Transactional;
import org.example.logistics_crm.user.User;
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

//    @Override
//    @Transactional
//    public User updateUser(Long id, ) {
//        if (id == null || id <= 0L) {
//            throw new IllegalArgumentException("User can't be null");
//        }
//        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
//        return userRepository.save(user);
//    }

    @Override
    public UserDetailsResponseDTO findById(Long clientId) {

        User user = userRepository.findById(clientId)
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
    public List<UserListResponseDTO> findByFirstNameAndLastName(String firstName, String lastName) {
        if (firstName == null || lastName == null || firstName.isBlank() || lastName.isBlank()) {
            throw new IllegalArgumentException("First name and last name can't be null");
        }

        List<User> byFirstNameAndLastName = userRepository.findByFirstNameAndLastName(firstName, lastName);

        return mapToList(byFirstNameAndLastName);
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
