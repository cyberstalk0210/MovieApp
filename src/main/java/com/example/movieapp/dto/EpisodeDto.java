package com.example.movieapp.dto;

import lombok.Data;

@Data
public class EpisodeDto {
    private Long episodeId;
    private Integer episodeNumber;
    private String title;
    private String thumbnail;
}
