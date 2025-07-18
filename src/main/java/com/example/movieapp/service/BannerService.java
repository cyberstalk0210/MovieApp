package com.example.movieapp.service;

import com.example.movieapp.dto.BannerDto;
import com.example.movieapp.entities.Banner;
import com.example.movieapp.entities.Series;
import com.example.movieapp.mapper.BannerMapper;
import com.example.movieapp.mapper.SeriesMapper;
import com.example.movieapp.repository.BannerRepo;
import com.example.movieapp.repository.EpisodeRepo;
import com.example.movieapp.repository.SeriesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepo bannerRepo;
    private final SeriesRepo seriesRepo;
    private final SeriesMapper seriesMapper;
    private final BannerMapper bannerMapper;

    public ResponseEntity<?> createBanner(BannerDto bannerDto, Long serisId) {
        Optional<Series> byId = seriesRepo.findById(serisId);
        if (!byId.isPresent())
            throw new RuntimeException("Series not found");

        bannerDto.setMovie(seriesMapper.toDto(byId.get()));
        bannerRepo.save(bannerMapper.toBanner(bannerDto));
        return ResponseEntity.ok().build();
    }


    public ResponseEntity<?> updateBanner(Long id, BannerDto bannerDto, Long serisId) {
        Series series = seriesRepo.findById(serisId)
                .orElseThrow(() -> new RuntimeException("Series not found"));

        Banner banner = bannerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));

        banner.setSeries(series);
        banner.setImage(bannerDto.getImage());
        bannerRepo.save(banner);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteBanner(Long id, Long serisId) {
        Optional<Series> byId = seriesRepo.findById(serisId);
        if (byId.isEmpty())
            throw new RuntimeException("Series not found");
        bannerRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
    public List<Banner> getAllBanners(){
        return bannerRepo.findAll();
    }
}
