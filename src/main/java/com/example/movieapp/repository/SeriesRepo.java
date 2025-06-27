package com.example.movieapp.repository;

import com.example.movieapp.entities.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepo extends JpaRepository<Series, Long> {

}
