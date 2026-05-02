package org.example.logistics_crm.controller;

import jakarta.validation.Valid;
import org.example.logistics_crm.dto.user.request.*;
import org.example.logistics_crm.dto.user.response.UserDetailsResponseDTO;
import org.example.logistics_crm.dto.user.response.UserListResponseDTO;
import org.example.logistics_crm.service.user.AccountUserService;
import org.example.logistics_crm.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final AccountUserService accountUserService;

    @Autowired
    public UserController(UserService userService, AccountUserService accountUserService) {
        this.userService = userService;
        this.accountUserService = accountUserService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDetailsResponseDTO createUser(
            @Valid @RequestBody CreateUserRequestDTO createUserRequestDTO) {
        return userService.createUser(createUserRequestDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsResponseDTO findById(
            @PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserListResponseDTO> searchUser(
            @ModelAttribute UserSearchRequestDTO requestDTO, Pageable pageable) {
        return userService.searchUser(requestDTO, pageable);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserListResponseDTO> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(
            @PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}/password")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsResponseDTO changePassword(
            @PathVariable("id") Long id, @Valid @RequestBody ChangeUserPasswordRequestDTO changeUserPasswordRequestDTO) {
        return accountUserService.changePassword(id, changeUserPasswordRequestDTO);
    }

    @PutMapping("/{id}/email")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsResponseDTO changeEmail(
            @PathVariable("id") Long id, @Valid @RequestBody ChangeUserEmailRequestDTO changeUserEmailRequestDTO) {
        return accountUserService.changeEmail(id, changeUserEmailRequestDTO);
    }

    @PutMapping("/{id}/phoneNumber")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsResponseDTO changePhoneNumber(
            @PathVariable("id") Long id, @Valid @RequestBody ChangeUserPhoneNumberDTO changeUserPhoneNumberDTO) {
        return accountUserService.changePhoneNumber(id, changeUserPhoneNumberDTO);
    }
}
