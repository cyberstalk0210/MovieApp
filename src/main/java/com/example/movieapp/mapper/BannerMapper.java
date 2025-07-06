package com.example.movieapp.mapper;

import com.example.movieapp.dto.AuthResponse;
import com.example.movieapp.dto.BannerDto;
import com.example.movieapp.dto.UserDto;
import com.example.movieapp.entities.Banner;
import com.example.movieapp.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BannerMapper {

    Banner toBanner(BannerDto bannerDto);
}
