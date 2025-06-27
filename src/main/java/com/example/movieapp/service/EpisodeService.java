package com.example.movieapp.service;

import com.example.movieapp.dto.EpisodeDto;
import com.example.movieapp.entities.Episode;
import com.example.movieapp.mapper.EpisodeMapper;
import com.example.movieapp.repository.EpisodeRepo;
import com.example.movieapp.repository.SeriesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class EpisodeService {

    private final EpisodeRepo episodeRepo;
    private final SeriesRepo seriesRepo;
    private final EpisodeMapper episodeMapper;


    public EpisodeDto getEpisodeById(Long seriesId, Long episodeId) {
        Episode episode = episodeRepo.findById(episodeId)
                .orElseThrow(() -> new RuntimeException("Episode not found"));

        if (!episode.getSeries().getId().equals(seriesId)) {
            throw new RuntimeException("Episode does not belong to this series");
        }

        return episodeMapper.toEpisodeDto(episode);
    }

}
