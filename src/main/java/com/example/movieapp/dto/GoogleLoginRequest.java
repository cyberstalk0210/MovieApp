package com.example.movieapp.dto;

import lombok.Data;

@Data
public class GoogleLoginRequest {
    private String credential;
    private String deviceId;
}
