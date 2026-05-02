package org.example.logistics_crm.service.user.impl;


import lombok.extern.slf4j.Slf4j;
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
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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

        log.debug("Attempting to create new user with email: {}, phoneNumber: {}"
                , createUserRequestDTO.email(), createUserRequestDTO.phoneNumber());

        if (userRepository.existsByEmail(createUserRequestDTO.email())) {
            throw new IllegalArgumentException("Email " + createUserRequestDTO.email() + " already exists");
        }

        if (userRepository.existsByPhoneNumber(createUserRequestDTO.phoneNumber())) {
            throw new IllegalArgumentException("Phone number " + createUserRequestDTO.phoneNumber() + " already exists");
        }

        String encodedPassword = passwordEncoder.encode(createUserRequestDTO.password());

        User user = userRepository.save(new User(
                createUserRequestDTO.firstName(),
                createUserRequestDTO.lastName(),
                createUserRequestDTO.email(),
                createUserRequestDTO.phoneNumber(),
                encodedPassword)
        );

        log.info("User with email: {}, phoneNumber: {} successfully created with id: {}"
                , createUserRequestDTO.email(), createUserRequestDTO.phoneNumber(), user.getId());
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
    @Transactional(readOnly = true)
    public UserDetailsResponseDTO findById(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id cannot be null or less than 0");
        }

        log.debug("Fetching user with id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID: " + userId + " not found"));

        return mapToDetails(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserListResponseDTO> searchUser(UserSearchRequestDTO requestDTO, Pageable pageable) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("User search request must not be null");
        }

        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide pagination parameters.");
        }

        log.debug("Searching for users with criteria: {}", requestDTO);
        Page<User> all = userRepository.findAll(UserSpecification.search(requestDTO), pageable);
        return pageToList(all);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserListResponseDTO> findAll(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null. Please provide pagination parameters.");
        }

        log.debug("Fetching all users, page request: {}", pageable);
        return pageToList(userRepository.findAll(pageable));
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id must be greater than 0");
        }

        log.debug("Attempting to delete user with id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        userRepository.delete(user);
        log.info("User with id: {} successfully deleted", userId);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserEntityById(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id must be greater than 0");
        }

        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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

    private Page<UserListResponseDTO> pageToList(Page<User> users) {
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
