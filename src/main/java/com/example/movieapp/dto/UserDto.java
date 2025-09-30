package com.example.movieapp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Boolean subscription;
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;
    private Long userId;
}
