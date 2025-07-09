package com.example.movieapp.controller;

import com.example.movieapp.dto.AuthResponse;
import com.example.movieapp.dto.SignInRequest;
import com.example.movieapp.dto.SignUpRequest;
import com.example.movieapp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody SignInRequest request){
        AuthResponse authResponse = authService.signIn(request);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        AuthResponse response = authService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
