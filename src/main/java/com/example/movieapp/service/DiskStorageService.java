package com.example.movieapp.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DiskStorageService implements StorageService {

    private final Path root = Paths.get("/uploads");
    
    @Override
    public String store(String path, byte[] data, String type) throws IOException {
        Path file = root.resolve(path);
        Files.createDirectories(file.getParent());
        Files.write(file, data);
        return file.toString();
    }
}
