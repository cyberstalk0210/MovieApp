package com.example.movieapp.service;

import java.io.IOException;

public interface StorageService {
    String store(String path, byte[] data, String type) throws IOException;
}
