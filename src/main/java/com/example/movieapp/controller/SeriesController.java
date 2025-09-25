package com.example.movieapp.controller;

import com.example.movieapp.dto.GetDetailsResponse;
import com.example.movieapp.dto.SeriesDto;
import com.example.movieapp.entities.Series;
import com.example.movieapp.entities.User;
import com.example.movieapp.repository.BannerRepo;
import com.example.movieapp.repository.SeriesRepo;
import com.example.movieapp.repository.UserRepo;
import com.example.movieapp.service.FileStorageService;
import com.example.movieapp.service.MovieAccessService;
import com.example.movieapp.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/series")

public class SeriesController {
    private final SeriesService seriesService;
    private final FileStorageService fileStorageService;
    private final BannerRepo bannerRepo;
    private final SeriesRepo seriesRepo;
    private final UserRepo userRepo;
    private final MovieAccessService movieAccessService;

    @GetMapping("/all")
    public ResponseEntity<List<SeriesDto>> series() {
        return seriesService.findAll();
    }

    @GetMapping("/{serialId}")
    public ResponseEntity<?> getDetails(@PathVariable Long serialId, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean canWatch = movieAccessService.canUserWatchMovie(user.getId(), serialId);

        if (!canWatch) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Siz bu serial ni ko'ra olmaysiz");
        }
        return ResponseEntity.ok(seriesService.getDetails(serialId));
    }


    @PostMapping("/add")
    public ResponseEntity<?> createSeries(@RequestParam("title") String title,
                                          @RequestParam("status") String status,
                                          @RequestParam("image") MultipartFile image) {
        String imagePath = fileStorageService.saveImage("series", image);

        SeriesDto dto = new SeriesDto();
        dto.setTitle(title);
        dto.setStatus(status);
        dto.setImagePath(imagePath);

        return seriesService.saveSeries(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeries(@PathVariable Long id,
                                          @RequestParam("title") String title,
                                          @RequestParam("status") String status,
                                          @RequestParam(value = "image", required = false) MultipartFile image) {

        Optional<Series> existing = seriesRepo.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();

        SeriesDto dto = new SeriesDto();
        dto.setTitle(title);
        dto.setStatus(status);

        if (image != null && !image.isEmpty()) {
            String imagePath = fileStorageService.saveImage("series", image);
            dto.setImagePath(imagePath);
        } else {

            dto.setImagePath(existing.get().getImagePath());
        }

        return seriesService.updateSeries(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSeries(@PathVariable Long id) {
        return seriesService.deleteSeries(id);
    }

}
