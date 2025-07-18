package com.example.movieapp.service;

import com.example.movieapp.dto.UserDto;
import com.example.movieapp.entities.User;
import com.example.movieapp.mapper.UserMapper;
import com.example.movieapp.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public User getUserByEmail(String email) {
        Optional<User> byEmail = userRepo.findByEmail(email);
        return byEmail.orElse(null);
    }

    public ResponseEntity<UserDto> updateUser(Long id, UserDto userDto) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        User user = optionalUser.get();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        if (Boolean.TRUE.equals(userDto.getSubscription()) && !Boolean.TRUE.equals(user.getSubscription())) {
            user.setSubscriptionStartDate(LocalDate.now()); // faqat yangi obuna bo'lsa
        }

        user.setSubscription(userDto.getSubscription());
        User updatedUser = userRepo.save(user);

        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void autoUpdateSubscriptions() {
        checkAndUpdateSubscriptions();
    }

    public void checkAndUpdateSubscriptions() {
        List<User> users = userRepo.findAll();
        LocalDate now = LocalDate.now();

        for (User user : users) {
            if (Boolean.TRUE.equals(user.getSubscription())
                    && user.getSubscriptionStartDate() != null
                    && user.getSubscriptionStartDate().plusDays(30).isBefore(now)) {
                user.setSubscription(false);
                userRepo.save(user);
            }
        }
    }


    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

}
