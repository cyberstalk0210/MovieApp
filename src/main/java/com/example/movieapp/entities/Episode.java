package com.example.movieapp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "episodes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String videoUrl;

    private Integer episodeNumber;

    private String title;

    private String thumbnail;

    private String fileName;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;
}
