package com.example.movieapp.controller;

import com.example.movieapp.dto.BannerDto;
import com.example.movieapp.dto.GetDetailsResponse;
import com.example.movieapp.dto.SeriesDto;
import com.example.movieapp.repository.BannerRepo;
import com.example.movieapp.service.FileStorageService;
import com.example.movieapp.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/series")

public class SeriesController {
    private final SeriesService seriesService;
    private final FileStorageService fileStorageService;
    private final BannerRepo bannerRepo;

    @GetMapping("/all")
    public ResponseEntity<List<SeriesDto>> series() {
        return seriesService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetDetailsResponse> getDetails(@PathVariable Long id) {
        return ResponseEntity.ok(seriesService.getDetails(id));
    }


    @PostMapping("/add")
    public ResponseEntity<?> createSeries(@RequestParam("title") String title,
                                          @RequestParam("status") String status,
                                          @RequestParam("image") MultipartFile image) {
        String imagePath = fileStorageService.saveImage(image);

        SeriesDto dto = new SeriesDto();
        dto.setTitle(title);
        dto.setStatus(status);
        dto.setImagePath(imagePath);

        return seriesService.saveSeries(dto);
    }
}
