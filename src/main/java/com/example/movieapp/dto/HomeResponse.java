package com.example.movieapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeResponse {
    private UserDto user;
    private List<BannerDto> banners;
    private List<SeriesDto> series;
}
