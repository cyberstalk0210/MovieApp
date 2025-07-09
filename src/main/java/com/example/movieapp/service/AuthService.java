package com.example.movieapp.service;

import com.example.movieapp.dto.AuthResponse;
import com.example.movieapp.dto.SignInRequest;
import com.example.movieapp.dto.SignUpRequest;
import com.example.movieapp.entities.User;
import com.example.movieapp.mapper.UserMapper;
import com.example.movieapp.repository.UserRepo;
import com.example.movieapp.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthResponse signIn(SignInRequest request) {
            User user = userRepo.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
                throw new RuntimeException("Invalid credentials");

        String token = jwtTokenProvider.generateToken(user.getEmail());

        AuthResponse response = userMapper.toAuthResponse(user);
        response.setToken(token);

        return response;
    }

    public AuthResponse signUp(SignUpRequest request) {
        if (userRepo.findByEmail(request.getEmail()).isPresent())
            throw new RuntimeException("User already exists");

        Long userId = generateUniqueUserId();

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .subscription(false)
                .userId(userId)
                .build();

        userRepo.save(user);

        String token = jwtTokenProvider.generateToken(user.getEmail());

        AuthResponse response = userMapper.toAuthResponse(user);
        response.setToken(token);

        return response;
    }


    public Long generateUniqueUserId() {
        Random random = new Random();
        Long userId;
        do {
            int number = 100000 + random.nextInt(900000);
            userId = (long) number;
        } while (userRepo.existsByUserId(userId));

        return userId;
    }


}
