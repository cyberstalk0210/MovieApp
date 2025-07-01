package com.example.movieapp.controller;

import com.example.movieapp.dto.EpisodeDto;
import com.example.movieapp.service.EpisodeService;
import com.example.movieapp.service.WasabiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/series")
@RequiredArgsConstructor
public class AdminEpisodeController {

    private final EpisodeService episodeService;
    private final WasabiService wasabiService;

    @GetMapping("/{seriesId}/episodes")
    public ResponseEntity<List<EpisodeDto>> getEpisodesBySeries(@PathVariable Long seriesId) {
        List<EpisodeDto> episodes = episodeService.getEpisodesBySeries(seriesId);
        return ResponseEntity.ok(episodes);
    }

    @PostMapping("/{seriesId}/episodes")
    public ResponseEntity<?> add(@PathVariable Long seriesId, @RequestBody EpisodeDto dto) {
        return episodeService.addEpisode(seriesId, dto);
    }

    @PutMapping("/episodes/{episodeId}")
    public ResponseEntity<?> update(@PathVariable Long episodeId, @RequestBody EpisodeDto dto) {
        return episodeService.updateEpisode(episodeId, dto);
    }

    @DeleteMapping("/episodes/{episodeId}")
    public ResponseEntity<?> delete(@PathVariable Long episodeId) {
        return episodeService.deleteEpisode(episodeId);
    }

    @PostMapping("/upload")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type
    ) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String contentType = type.equals("image") ? "image/jpeg" : "video/mp4";
            wasabiService.uploadFile(fileName, file.getBytes(), contentType);
            String url = wasabiService.generateFileUrl(fileName);
            return ResponseEntity.ok(Map.of("url", url));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Fayl yuklashda xato: " + e.getMessage()));
        }
    }
}
