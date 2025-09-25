package com.example.movieapp.repository;

import com.example.movieapp.entities.MovieAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MovieAccessRepository extends JpaRepository<MovieAccess, Long> {
    boolean existsByUserIdAndMovieIdAndPaidTrue(Long userId, Long movieId);

    boolean existsByUserIdAndMovieId(Long userId, Long seriesId);

    void deleteByMovie_Id(Long movieId);

    Collection<MovieAccess> findByUserIdAndPaidTrue(Long userId);

    List<MovieAccess> findByPaidTrueAndCreatedAtBefore(LocalDateTime oneMonthAgo);

    Optional<MovieAccess> findByUserIdAndMovieIdAndPaidTrue(Long userId, Long movieId);

    List<MovieAccess> findByUserId(Long userId);

}
