package com.healthmed.application.adapters.controllers;

import com.healthmed.domain.dtos.AuthDTO;
import com.healthmed.domain.ports.interfaces.AuthServicePort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthServicePort service;
    public AuthController(AuthServicePort service) {
        this.service = service;
    }

    @PostMapping("/login")
    public AuthDTO login(@RequestParam String email, @RequestParam String password) {
        return service.login(email, password);
    }
}
