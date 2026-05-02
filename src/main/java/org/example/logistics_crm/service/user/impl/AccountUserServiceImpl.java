package org.example.logistics_crm.service.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.example.logistics_crm.entity.user.User;
import org.example.logistics_crm.service.user.AccountUserService;
import org.example.logistics_crm.service.user.UserService;
import org.example.logistics_crm.dto.user.request.ChangeUserEmailRequestDTO;
import org.example.logistics_crm.dto.user.request.ChangeUserPasswordRequestDTO;
import org.example.logistics_crm.dto.user.request.ChangeUserPhoneNumberDTO;
import org.example.logistics_crm.dto.user.response.UserDetailsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountUserServiceImpl implements AccountUserService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountUserServiceImpl(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetailsResponseDTO changePassword(Long userId, ChangeUserPasswordRequestDTO changeUserPasswordRequestDTO) {
        log.debug("Attempting to change password for user with id: {}", userId);
        User user = validateUserAndPassword(userId, changeUserPasswordRequestDTO.oldPassword());

        String newPassword = changeUserPasswordRequestDTO.newPassword();

        if (!newPassword.equals(changeUserPasswordRequestDTO.confirmNewPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new IllegalArgumentException("New password must be different from the old password");
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        log.info("Changing password for user with id: {} successful", userId);
        return mapToDetails(user);
    }

    @Override
    @Transactional
    public UserDetailsResponseDTO changeEmail(Long userId, ChangeUserEmailRequestDTO changeUserEmailRequestDTO) {
        log.debug("Attempting to change email for user with id: {}", userId);
        User user = validateUserAndPassword(userId, changeUserEmailRequestDTO.currentPassword());

        String newEmail = changeUserEmailRequestDTO.newEmail();

        if (user.getEmail().equals(newEmail)) {
            throw new IllegalArgumentException("New email is the same as current email");
        }

        if (userService.existsByEmail(newEmail, userId)) {
            throw new IllegalArgumentException("Email " + newEmail + " already exists");
        }

        user.setEmail(newEmail);
        log.info("Changing email for user with id: {} successful", userId);
        return mapToDetails(user);
    }

    @Override
    @Transactional
    public UserDetailsResponseDTO changePhoneNumber(Long userId, ChangeUserPhoneNumberDTO changeUserPhoneNumberDTO) {
        log.debug("Attempting to change phone number for user with id: {}", userId);
        User user = validateUserAndPassword(userId, changeUserPhoneNumberDTO.currentPassword());

        String newPhoneNumber = changeUserPhoneNumberDTO.newPhoneNumber();

        if (user.getPhoneNumber().equals(newPhoneNumber)) {
            throw new IllegalArgumentException("New phone number is the same as current phone number");
        }

        if (userService.existsByPhoneNumber(newPhoneNumber, user.getId())) {
            throw new IllegalArgumentException("Phone number " + newPhoneNumber + " already exists");
        }

        user.setPhoneNumber(newPhoneNumber);
        log.info("Changing phone number for user with id: {} successful", userId);
        return mapToDetails(user);
    }

    private User validateUserAndPassword(Long userId, String currentPassword) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id must be greater than 0");
        }

        User userEntityById = userService.getUserEntityById(userId);

        if (!passwordEncoder.matches(currentPassword, userEntityById.getPassword())) {
            log.warn("Security alert: Failed password change attempt for user ID: {} - incorrect current password", userId);
            throw new IllegalArgumentException("Current password user " + userId + " is incorrect ");
        }

        return userEntityById;
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
}
