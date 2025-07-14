package com.example.movieapp.service;

import com.example.movieapp.dto.AuthResponse;
import com.example.movieapp.dto.SignInRequest;
import com.example.movieapp.dto.SignUpRequest;
import com.example.movieapp.entities.RefreshToken;
import com.example.movieapp.entities.User;
import com.example.movieapp.mapper.UserMapper;
import com.example.movieapp.repository.RefreshTokenRepository;
import com.example.movieapp.repository.UserRepo;
import com.example.movieapp.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthResponse signIn(SignInRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());

        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUser(user);

        String refreshToken;
        if (existingToken.isPresent() && existingToken.get().getExpiryDate().isAfter(Instant.now())) {
            refreshToken = existingToken.get().getToken();
        } else {
            refreshToken = refreshTokenService.createRefreshToken(user.getEmail()).getToken();
        }

        AuthResponse response = userMapper.toAuthResponse(user);
        response.setToken(accessToken);
        response.setRefreshToken(refreshToken);

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

        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = refreshTokenService.createRefreshToken(user.getEmail()).getToken();

        AuthResponse response = userMapper.toAuthResponse(user);
        response.setToken(accessToken);
        response.setRefreshToken(refreshToken);

        return response;
    }

    public void logout(String email) {
        User user = userRepo.findByEmail(email).orElseThrow();
        refreshTokenService.deleteByUser(user);
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

