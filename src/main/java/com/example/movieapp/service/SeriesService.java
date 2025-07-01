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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        return GetDetailsResponse.builder()
                .id(series.getId())
                .title(series.getTitle())
                .parts(episodeMapper.toPartDtoList(episodes))
                .build();
    }

    public ResponseEntity<Map<String, Object>> saveSeries(SeriesDto seriesDto) {
        Series series = seriesMapper.toEntity(seriesDto);

        Series saved = seriesRepo.save(series);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of("message", "Series saved successfully", "id", saved.getId())
        );
    }

    public ResponseEntity<Map<String, Object>> updateSeries(Long seriesId, SeriesDto seriesDto) {
        return seriesRepo.findById(seriesId)
                .map(series -> {
                    series.setTitle(seriesDto.getTitle());
                    series.setStatus(seriesDto.getStatus());
                    series.setImage(seriesDto.getImage());
                    Series updated = seriesRepo.save(series);

                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "Series updated successfully");
                    response.put("id", updated.getId());

                    return ResponseEntity.ok(response);
                }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Map<String, Object>> deleteSeries(Long seriesId) {
        return seriesRepo.findById(seriesId)
                .map(series -> {
                    seriesRepo.delete(series);
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "Series deleted successfully");
                    response.put("id", seriesId);
                    return ResponseEntity.ok(response);
                }).orElse(ResponseEntity.notFound().build());
    }


}
