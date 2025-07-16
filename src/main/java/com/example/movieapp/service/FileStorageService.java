package com.example.movieapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    private final String uploadRootDir = "/uploads";

    public String saveImage(String type, MultipartFile file) {
        try {
            Path uploadPath = Paths.get(uploadRootDir, type);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            file.transferTo(filePath.toFile());

            return "/uploads/" + type + "/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Rasmni saqlab bo‘lmadi: " + e.getMessage(), e);
        }
    }
}

