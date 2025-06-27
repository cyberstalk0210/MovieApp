package com.example.movieapp.service;

import com.example.movieapp.dto.AuthResponse;
import com.example.movieapp.dto.SignInRequest;
import com.example.movieapp.dto.SignUpRequest;
import com.example.movieapp.entities.User;
import com.example.movieapp.mapper.UserMapper;
import com.example.movieapp.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthResponse signIn(SignInRequest request) {

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        return userMapper.maptoAuthResponse(user);
    }

    public AuthResponse signUp(SignUpRequest request) {
        if (userRepo.findByEmail(request.getEmail()).isPresent())
            throw new RuntimeException("User already exists");


        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .subscription(false)
                .userId(System.currentTimeMillis())
                .build();

        return userMapper.maptoAuthResponse(user);
    }

}
