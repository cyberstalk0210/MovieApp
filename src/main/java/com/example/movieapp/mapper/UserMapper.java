package com.example.movieapp.mapper;

import com.example.movieapp.dto.AuthResponse;
import com.example.movieapp.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    AuthResponse maptoAuthResponse(User user);
}
