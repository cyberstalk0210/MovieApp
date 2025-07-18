package com.example.movieapp.controller;

import com.example.movieapp.dto.*;
import com.example.movieapp.entities.User;
import com.example.movieapp.entities.UserDevice;
import com.example.movieapp.repository.UserDeviceRepository;
import com.example.movieapp.repository.UserRepo;
import com.example.movieapp.security.JwtTokenProvider;
import com.example.movieapp.service.AuthService;
import com.example.movieapp.service.RefreshTokenService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserRepo userRepo;
    private final UserDeviceRepository userDeviceRepository;


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

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
        try {
            refreshTokenService.validateRefreshToken(request.getRefreshToken());
            String email = refreshTokenService.getEmailFromToken(request.getRefreshToken());

            String newAccessToken = jwtTokenProvider.generateAccessToken(email);
            String newRefreshToken = refreshTokenService.createRefreshToken(email).getToken();

            // ✅ USERNI OLIB, deviceId orqali tokenni yangilaymiz
            User user = userRepo.findByEmail(email).orElseThrow();
            Optional<UserDevice> userDeviceOpt = userDeviceRepository.findByUserId(user.getId());

            if (userDeviceOpt.isPresent()) {
                UserDevice device = userDeviceOpt.get();
                device.setToken(newAccessToken);
                device.setCreatedAt(Instant.now());
                userDeviceRepository.save(device);
            }

            AuthResponse response = new AuthResponse();
            response.setToken(newAccessToken);
            response.setRefreshToken(newRefreshToken);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest request) {
        authService.logout(request.getEmail());
        return ResponseEntity.ok("User logged out successfully");
    }

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody GoogleLoginRequest request) {
        GoogleIdToken idToken = authService.verifyGoogleToken(request.getCredential());
        return authService.getAuthResponseResponseEntity(idToken, request.getDeviceId());
    }

}
