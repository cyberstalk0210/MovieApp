package com.example.movieapp.service;

import com.example.movieapp.dto.GetDetailsResponse;
import com.example.movieapp.dto.SeriesDto;
import com.example.movieapp.entities.Episode;
import com.example.movieapp.entities.Series;
import com.example.movieapp.mapper.EpisodeMapper;
import com.example.movieapp.mapper.SeriesMapper;
import com.example.movieapp.repository.EpisodeRepo;
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
    private final EpisodeRepo episodeRepo;
    private final EpisodeMapper episodeMapper;

    public ResponseEntity<List<SeriesDto>> findAll() {
        List<SeriesDto> series = seriesRepo.findAll().stream()
                .map(seriesMapper::toDto)
                .toList();
        return ResponseEntity.ok(series);
    }

    public GetDetailsResponse getDetails(Long seriesId) {
        Series series = seriesRepo.findById(seriesId)
                .orElseThrow(() -> new RuntimeException("Series not found"));

        List<Episode> episodes = episodeRepo.findBySeriesId(seriesId);

        GetDetailsResponse response = new GetDetailsResponse();
        response.setId(series.getId());
        response.setTitle(series.getTitle());
        response.setParts(episodeMapper.toPartDtoList(episodes));

        return response;
    }
}
