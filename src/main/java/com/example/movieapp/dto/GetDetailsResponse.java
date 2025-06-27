package com.example.movieapp.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetDetailsResponse {
    private Long id;
    private String title;
    private List<EpisodePartDto> parts;
}
