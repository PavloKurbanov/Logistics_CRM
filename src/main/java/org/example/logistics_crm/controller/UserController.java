package org.example.logistics_crm.controller;

import org.example.logistics_crm.service.client.AccountClientService;
import org.example.logistics_crm.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final AccountClientService accountClientService;

    @Autowired
    public UserController(UserService userService, AccountClientService accountClientService) {
        this.userService = userService;
        this.accountClientService = accountClientService;
    }




}
