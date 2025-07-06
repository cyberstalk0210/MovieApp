package com.example.movieapp.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Boolean subscription;
}
