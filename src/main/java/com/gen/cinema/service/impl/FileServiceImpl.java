package com.gen.cinema.service.impl;

import com.gen.cinema.config.MinioProperties;
import com.gen.cinema.exception.BadRequestAlertException;
import com.gen.cinema.service.FileService;
import io.minio.MinioClient;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.http.Method;
import io.minio.StatObjectArgs;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
    private final MinioClient minioClient;
    private final MinioClient presignedUrlClient;
    private final MinioProperties minioProperties;

    public FileServiceImpl(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
        log.debug("Initializing MinIO clients with internal endpoint: {} and external endpoint: {}", 
            minioProperties.getInternalEndpoint(), 
            minioProperties.getExternalEndpoint());
        
        // Client for internal operations
        this.minioClient = MinioClient.builder()
                .endpoint(minioProperties.getInternalEndpoint())  // Use minio:9000 for internal operations
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
                
        // Client for generating presigned URLs
        this.presignedUrlClient = MinioClient.builder()
                .endpoint(minioProperties.getExternalEndpoint())  // Use IP address for presigned URLs
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

    @Override
    public String generatePresignedUploadUrl(String objectPath, long expirySeconds) {
        try {
            log.debug("Generating presigned URL for object: {}", objectPath);
            log.debug("Using external endpoint: {}", minioProperties.getExternalEndpoint());
            
            return presignedUrlClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(minioProperties.getBucket())
                            .object(objectPath)
                            .expiry((int) expirySeconds, TimeUnit.SECONDS)
                            .build()
            );
        } catch (Exception e) {
            log.error("Failed to generate presigned URL", e);
            throw new BadRequestAlertException("Failed to generate presigned URL: " + e.getMessage());
        }
    }

    @Override
    public String generatePresignedDownloadUrl(String objectPath) {
        try {            
            return presignedUrlClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(minioProperties.getBucket())
                            .object(objectPath)
                            .expiry((int) 3600, TimeUnit.SECONDS)
                            .build()
            );
        } catch (Exception e) {
            log.error("Failed to generate presigned download URL", e);
            throw new BadRequestAlertException("Failed to generate presigned download URL: " + e.getMessage());
        }
    }

    @Override
    public boolean fileExists(String objectPath) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(minioProperties.getBucket())
                            .object(objectPath)
                            .build()
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }
} 