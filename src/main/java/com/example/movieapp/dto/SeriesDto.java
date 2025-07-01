package com.example.movieapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class SeriesDto {

    private Long id;

    @NotBlank
    private String image;

    @NotBlank
    private String title;

    @NotBlank
    private String status;
}