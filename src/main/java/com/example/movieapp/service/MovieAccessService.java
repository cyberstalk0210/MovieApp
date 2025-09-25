package com.example.movieapp.service;

import com.example.movieapp.entities.MovieAccess;
import com.example.movieapp.entities.Series;
import com.example.movieapp.entities.User;
import com.example.movieapp.repository.MovieAccessRepository;
import com.example.movieapp.repository.SeriesRepo;
import com.example.movieapp.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieAccessService {

    private final MovieAccessRepository movieAccessRepository;
    private final UserRepo userRepository;
    private final SeriesRepo seriesRepository;

    public boolean canUserWatchMovie(Long userId, Long movieId) {
        return movieAccessRepository.existsByUserIdAndMovieIdAndPaidTrue(userId, movieId);
    }

    public MovieAccess giveAccess(Long userId, Long seriesId, boolean paid) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new RuntimeException("Series not found"));

        if (movieAccessRepository.existsByUserIdAndMovieId(userId, seriesId)) {
            log.error("Movie access already exists");
            throw new RuntimeException("User is already watching movie access");
        }
        MovieAccess access = new MovieAccess();
        access.setUser(user);
        access.setMovie(series);
        access.setPaid(paid);

        log.debug("Movie access has been given, {} , {}", access.getUser().getUsername(), access.getMovie());
        return movieAccessRepository.save(access);
    }

    public List<Series> getUserAccessedSeries(Long userId) {
        log.debug("Getting series access IDs for user {}", userId);
        return movieAccessRepository.findByUserIdAndPaidTrue(userId)
                .stream()
                .map(MovieAccess::getMovie)
                .toList();
    }

    public List<MovieAccess> getAllMovies() {
        return movieAccessRepository.findAll();
    }

    @Transactional
    public ResponseEntity<?> deleteAccess(Long userId, Long movieId) {
        Optional<MovieAccess> accessOpt = movieAccessRepository
                .findByUserIdAndMovieIdAndPaidTrue(userId, movieId);

        if (accessOpt.isEmpty()) {
            throw new RuntimeException("User does not have movie access");
        }

        log.debug("Movie access has been deleted, {} , {}", accessOpt.get().getUser().getUsername(), accessOpt.get().getMovie());
        movieAccessRepository.delete(accessOpt.get());
        return ResponseEntity.ok("Movie access deleted for userId=" + userId + ", movieId=" + movieId);
    }

    @Transactional
    public void removeExpiredAccesses() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<MovieAccess> expired = movieAccessRepository.findByPaidTrueAndCreatedAtBefore(oneMonthAgo);
        if (!expired.isEmpty()) {
            log.debug("Removing expired movies, {}", expired.size());
            movieAccessRepository.deleteAll(expired);
        }
    }

    @Transactional
    public void updateUserAccess(Long userId, Set<Long> newSeriesIds) {
        // mavjud access-larni olish
        List<MovieAccess> currentAccesses = movieAccessRepository.findByUserId(userId);

        // olib tashlanishi kerak bo‘lganlar
        log.debug("Updating user access IDs for user {}", userId);
        currentAccesses.stream()
                .filter(access -> !newSeriesIds.contains(access.getMovie().getId()))
                .forEach(movieAccessRepository::delete);

        // yangi access qo‘shish
        for (Long seriesId : newSeriesIds) {
            if (currentAccesses.stream().noneMatch(a -> a.getMovie().getId().equals(seriesId))) {
                log.debug("Adding series access for userId={}, seriesId={}", userId, seriesId);
                giveAccess(userId, seriesId, true);
            }
        }
    }

}
