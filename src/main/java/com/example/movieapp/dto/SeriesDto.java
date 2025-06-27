package com.example.movieapp.dto;

import lombok.Data;
import java.util.List;

@Data
public class SeriesDto {
    private Long id;
    private String image;
    private String title;
    private String status;
    private List<EpisodeDto> episodes;
}