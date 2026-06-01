package org.example.logistics_crm.controller;

import org.example.logistics_crm.dto.auth.RegistrationDTO;
import org.example.logistics_crm.dto.client.request.CreateClientRequestDTO;
import org.example.logistics_crm.dto.user.request.CreateUserRequestDTO;
import org.example.logistics_crm.service.client.ClientService;
import org.example.logistics_crm.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final UserService userService;
    private final ClientService clientService;


    @Autowired
    public AuthController(UserService userService, ClientService clientService) {
        this.userService = userService;
        this.clientService = clientService;
    }

    @RequestMapping(value = "/clients/registration", method = RequestMethod.POST)
    public String registrationClient(
            @RequestBody CreateClientRequestDTO createClientRequestDTO) {
        return clientService.register(createClientRequestDTO);
    }

    @RequestMapping(value = "/users/registration", method = RequestMethod.POST)
    public String registrationUser(
            @RequestBody CreateUserRequestDTO createUserRequestDTO) {
        return userService.register(createUserRequestDTO);
    }

    @PostMapping("/users/login")
    public String loginUser(
            @RequestBody RegistrationDTO registrationDTO) {
        return userService.login(registrationDTO.login(), registrationDTO.password());
    }

    @PostMapping("/clients/login")
    public String loginClient(
            @RequestBody RegistrationDTO registrationDTO) {
        return clientService.login(registrationDTO.login(), registrationDTO.password());
    }
}
