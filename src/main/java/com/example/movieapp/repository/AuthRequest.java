package com.example.movieapp.repository;

import com.example.movieapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRequest extends JpaRepository<User,Integer> {

}
