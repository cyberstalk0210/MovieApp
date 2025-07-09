package com.example.movieapp.repository;

import com.example.movieapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    boolean existsByUserId(Long userId);

}
