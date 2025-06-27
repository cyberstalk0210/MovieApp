package com.example.movieapp.mapper;

import com.example.movieapp.dto.AuthResponse;
import com.example.movieapp.dto.UserDto;
import com.example.movieapp.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    AuthResponse toAuthResponse(User user);

    UserDto toUserDto(User user);
}
