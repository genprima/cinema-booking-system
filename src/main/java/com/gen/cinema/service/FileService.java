package com.gen.cinema.service;

public interface FileService {
    String generatePresignedUploadUrl(String objectPath, long expirySeconds);
    String generatePresignedDownloadUrl(String objectPath);
    boolean fileExists(String objectPath);
} 