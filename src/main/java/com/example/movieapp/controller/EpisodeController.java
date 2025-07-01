package com.example.movieapp.controller;

import com.example.movieapp.dto.EpisodeDto;
import com.example.movieapp.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/series")
@RequiredArgsConstructor
public class EpisodeController {
    private final EpisodeService episodeService;

    @GetMapping("/{sid}/episode/{eid}")
    public ResponseEntity<EpisodeDto> getEpisode(
            @PathVariable Long sid,
            @PathVariable Long eid
    ) {
        return ResponseEntity.ok(episodeService.getEpisodeById(sid, eid));
    }

    @GetMapping("/{seriesId}/episodes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EpisodeDto>> getEpisodesBySeries(@PathVariable Long seriesId) {
        List<EpisodeDto> episodes = episodeService.getEpisodesBySeries(seriesId);
        return ResponseEntity.ok(episodes);
    }
}
