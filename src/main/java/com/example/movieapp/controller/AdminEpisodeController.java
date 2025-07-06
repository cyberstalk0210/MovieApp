package com.example.movieapp.controller;

import com.example.movieapp.dto.EpisodeDto;
import com.example.movieapp.dto.SeriesDto;
import com.example.movieapp.entities.Episode;
import com.example.movieapp.mapper.EpisodeMapper;
import com.example.movieapp.service.EpisodeService;
import com.example.movieapp.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/series")
@RequiredArgsConstructor
public class AdminEpisodeController {

    private final EpisodeService episodeService;
    private final EpisodeMapper episodeMapper;
    private final SeriesService seriesService;


    @GetMapping("/{seriesId}/episodes")
    public ResponseEntity<List<EpisodeDto>> getEpisodesBySeries(@PathVariable Long seriesId) {
        List<EpisodeDto> episodes = episodeService.getEpisodesBySeries(seriesId);
        return ResponseEntity.ok(episodes);
    }

    @PostMapping("/{seriesId}/episodes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EpisodeDto> addEpisodeToSeries(@PathVariable Long seriesId, @RequestBody EpisodeDto episodeDto) {
        episodeDto.setSeriesId(seriesId);
        Episode episodeEntity = episodeMapper.toEpisodeEntity(episodeDto);
        episodeService.addEpisode(seriesId, episodeDto);
        return ResponseEntity.ok(episodeMapper.toEpisodeDto(episodeEntity));
    }

    @PostMapping("/add-episode")
    public ResponseEntity<EpisodeDto> addEpisode(@RequestBody EpisodeDto episodeDto) {

        Episode episodeEntity = episodeMapper.toEpisodeEntity(episodeDto);

        episodeService.addEpisode(episodeDto.getSeriesId(),episodeDto);

        return ResponseEntity.ok(episodeMapper.toEpisodeDto(episodeEntity));
    }

    @PostMapping("/add-series")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> addSeries(@RequestBody SeriesDto seriesDto) {
        return seriesService.saveSeries(seriesDto);
    }


    @PutMapping("/episodes/{episodeId}")
    public ResponseEntity<?> updateEpisode(@PathVariable Long episodeId, @RequestBody EpisodeDto dto) {
        return episodeService.updateEpisode(episodeId, dto);
    }

    @DeleteMapping("/episodes/{episodeId}")
    public ResponseEntity<?> deleteEpisode(@PathVariable Long episodeId) {
        return episodeService.deleteEpisode(episodeId);
    }

}
