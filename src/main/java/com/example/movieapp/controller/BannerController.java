package com.example.movieapp.controller;

import com.example.movieapp.entities.Banner;
import com.example.movieapp.repository.BannerRepo;
import com.example.movieapp.repository.SeriesRepo;
import com.example.movieapp.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/series")
public class BannerController {
    private final FileStorageService fileStorageService;
    private final SeriesRepo seriesRepo;
    private final BannerRepo bannerRepo;

    @PostMapping("/banners/add")
    public ResponseEntity<?> addBanner(@RequestParam("seriesId") Long seriesId,
                                       @RequestParam("image") MultipartFile image) {
        String imagePath = fileStorageService.saveImage("banners", image);

        Banner banner = new Banner();
        banner.setSeries(seriesRepo.findById(seriesId).orElseThrow());
        banner.setImage(imagePath);

        bannerRepo.save(banner);

        return ResponseEntity.ok(Map.of("message", "Banner qo‘shildi", "image", imagePath));
    }

}
