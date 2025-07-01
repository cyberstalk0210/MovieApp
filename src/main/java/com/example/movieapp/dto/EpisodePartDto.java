package com.example.movieapp.dto;

import lombok.Data;

@Data
public class EpisodePartDto {
    private Long episodeId;
    private int episodeNumber;
    private String title;
    private String thumbnail;
}
