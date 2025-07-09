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

    private final String uploadDir = "/var/www/myapp/backend/uploads/";

    public String saveImage(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            file.transferTo(filePath.toFile());

            return uploadDir + filename;
        } catch (IOException e) {
            throw new RuntimeException("Rasmni saqlab bo‘lmadi", e);
        }
    }
}
