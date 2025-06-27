package com.example.movieapp.controller;

import com.example.movieapp.dto.GetDetailsResponse;
import com.example.movieapp.dto.SeriesDto;
import com.example.movieapp.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/series")
public class SeriesController {
    private final SeriesService seriesService;

    @GetMapping("/")
    public ResponseEntity<List<SeriesDto>> series() {
        return seriesService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetDetailsResponse> getDetails(@PathVariable Long id) {
        return ResponseEntity.ok(seriesService.getDetails(id));
    }

}
