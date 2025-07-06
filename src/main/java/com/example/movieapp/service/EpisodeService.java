package com.example.movieapp.service;

import com.example.movieapp.dto.EpisodeDto;
import com.example.movieapp.entities.Episode;
import com.example.movieapp.entities.Series;
import com.example.movieapp.mapper.EpisodeMapper;
import com.example.movieapp.repository.EpisodeRepo;
import com.example.movieapp.repository.SeriesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class EpisodeService {

    private final EpisodeRepo episodeRepo;
    private final EpisodeMapper episodeMapper;
    private final SeriesRepo seriesRepo;

    public EpisodeDto getEpisodeById(Long seriesId, Long episodeId) {

        Episode episode = episodeRepo.findById(episodeId)
                .orElseThrow(() -> new RuntimeException("Episode not found"));

        if (!episode.getSeries().getId().equals(seriesId)) {
            throw new RuntimeException("Episode does not belong to this series");
        }

        return episodeMapper.toEpisodeDto(episode);
    }
    public EpisodeDto addEpisode(Long seriesId, EpisodeDto dto) {
        Series series = seriesRepo.findById(seriesId)
                .orElseThrow(() -> new RuntimeException("Series not found"));

        Episode episode = Episode.builder()
                .title(dto.getTitle())
                .episodeNumber(dto.getEpisodeNumber())
                .thumbnail(series.getImagePath())
                .fileName(dto.getFileName())
                .videoUrl(dto.getVideoUrl())
                .series(series)
                .build();

        episodeRepo.save(episode);

        seriesRepo.save(series);

        return episodeMapper.toEpisodeDto(episode);
    }


    public ResponseEntity<Map<String, Object>> updateEpisode(Long episodeId, EpisodeDto dto) {
        return episodeRepo.findById(episodeId)
                .map(episode -> {
                    episode.setTitle(dto.getTitle());
                    episode.setEpisodeNumber(dto.getEpisodeNumber());

                    if (dto.getThumbnail() != null) {
                        episode.setThumbnail(dto.getThumbnail());
                    }

                    if (dto.getFileName() != null) {
                        episode.setFileName(dto.getFileName());
                        episode.setVideoUrl(dto.getVideoUrl());
                    }

                    episodeRepo.save(episode);
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "Episode updated");
                    response.put("id", episode.getId());
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    public ResponseEntity<Map<String, String>> deleteEpisode(Long episodeId) {
        return episodeRepo.findById(episodeId)
                .map(ep -> {
                    episodeRepo.delete(ep);
                    return ResponseEntity.ok(Map.of("message", "Episode deleted"));
                }).orElse(ResponseEntity.notFound().build());
    }

    public List<EpisodeDto> getEpisodesBySeries(Long seriesId) {
        List<Episode> episodes = episodeRepo.findBySeriesId(seriesId);
        return episodes.stream()
                .map(episodeMapper::toEpisodeDto)
                .toList();
    }

}
