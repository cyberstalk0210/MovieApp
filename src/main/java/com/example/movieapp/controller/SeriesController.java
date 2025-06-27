package com.example.movieapp.controller;

import com.example.movieapp.dto.SeriesDto;
import com.example.movieapp.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class SeriesController {
    private final SeriesService seriesService;

    @GetMapping("/series")
    public ResponseEntity<List<SeriesDto>> series() {
        return seriesService.findAll();
    }

}
