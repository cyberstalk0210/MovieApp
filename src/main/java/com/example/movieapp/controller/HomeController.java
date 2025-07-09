package com.example.movieapp.controller;

import com.example.movieapp.dto.HomeResponse;
import com.example.movieapp.entities.User;
import com.example.movieapp.repository.UserRepo;
import com.example.movieapp.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;
    private final UserRepo userRepo;

    @GetMapping("/home")
    public ResponseEntity<HomeResponse> home(Authentication authentication) {
        String email = authentication.getName();

        User user = userRepo.findByEmail(email).orElseThrow();

        return ResponseEntity.ok(homeService.getHomeData(user.getId()));
    }
}
