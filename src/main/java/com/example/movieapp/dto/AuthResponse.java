
package com.example.movieapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String username;
    private String email;
    private Boolean subscription;
    private Long userId;
    private String token; // access token
    private String refreshToken;
}