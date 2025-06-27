package com.example.movieapp.entities;

import com.example.movieapp.dto.SeriesDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "banners")
@Data
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;
}
