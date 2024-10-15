package com.socialmedia.controller;

import com.socialmedia.DTO.AuthenticationRequest;
import com.socialmedia.DTO.AuthenticationResponse;
import com.socialmedia.DTO.RegisterRequest;
import com.socialmedia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return "redirect:/api/auth/login";
    }

    @PostMapping("/perform-login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse response = authService.authenticate(authenticationRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    // Serve the login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

}

