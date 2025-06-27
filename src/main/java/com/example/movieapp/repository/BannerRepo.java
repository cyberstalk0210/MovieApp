package com.example.movieapp.repository;

import com.example.movieapp.entities.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepo extends JpaRepository<Banner, Long> {
}
