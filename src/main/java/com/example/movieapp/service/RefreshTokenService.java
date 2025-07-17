package com.example.movieapp.service;

import com.example.movieapp.entities.RefreshToken;
import com.example.movieapp.entities.User;
import com.example.movieapp.repository.RefreshTokenRepository;
import com.example.movieapp.repository.UserRepo;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepo userRepo;

    private final long refreshTokenDurationMs = 60L * 24 * 3600_000L; // 30 kun

    @Transactional
    public RefreshToken createRefreshToken(String email) {
        User user = userRepo.findByEmail(email).orElseThrow();

        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

    if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }

    }

    @Transactional
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

    public String getEmailFromToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        return refreshToken.getUser().getEmail();
    }
}
