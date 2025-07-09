package com.example.movieapp.service;

import com.example.movieapp.dto.BannerDto;
import com.example.movieapp.dto.HomeResponse;
import com.example.movieapp.dto.SeriesDto;
import com.example.movieapp.entities.Banner;
import com.example.movieapp.entities.Series;
import com.example.movieapp.entities.User;
import com.example.movieapp.mapper.SeriesMapper;
import com.example.movieapp.mapper.UserMapper;
import com.example.movieapp.repository.BannerRepo;
import com.example.movieapp.repository.SeriesRepo;
import com.example.movieapp.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final UserRepo userRepo;
    private final SeriesRepo seriesRepo;
    private final BannerRepo bannerRepo;
    private final UserMapper userMapper;
    private final SeriesMapper seriesMapper;

    public HomeResponse getHomeData(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<SeriesDto> seriesList = seriesRepo.findAll().stream()
                .map(seriesMapper::toDto)
                .toList();

        List<BannerDto> bannerDtos = bannerRepo.findAll().stream()
                .map(banner -> {
                    Series series = banner.getSeries();
                    SeriesDto movie = (series != null) ? seriesMapper.toDto(series) : null;

                    return BannerDto.builder()
                            .image(banner.getImage())
                            .movie(movie)
                            .build();
                }).toList();

        return HomeResponse.builder()
                .user(userMapper.toUserDto(user))
                .series(seriesList)
                .banners(bannerDtos)
                .build();
    }
}

