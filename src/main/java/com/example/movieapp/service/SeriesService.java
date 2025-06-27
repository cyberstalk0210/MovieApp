package com.example.movieapp.service;

import com.example.movieapp.dto.SeriesDto;
import com.example.movieapp.mapper.SeriesMapper;
import com.example.movieapp.repository.SeriesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeriesService {

    private final SeriesRepo seriesRepo;
    private final SeriesMapper seriesMapper;

    public ResponseEntity<List<SeriesDto>> findAll() {
        List<SeriesDto> series = seriesRepo.findAll().stream()
                .map(seriesMapper::toDto)
                .toList();
        return ResponseEntity.ok(series);
    }
}
