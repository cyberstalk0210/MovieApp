package com.example.movieapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class WasabiService {

    @Value("${wasabi.bucket-name}")
    private String bucketName;

    private final S3Client s3Client;

    public String generateFileUrl(String fileName) {
        return "https://s3.wasabisys.com/" + bucketName + "/" +
                URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    }

    public void uploadFile(String fileName, byte[] data, String contentType) {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .contentType(contentType)
                        .acl(ObjectCannedACL.PUBLIC_READ)
                        .build(),
                RequestBody.fromBytes(data)
        );
    }

    public void deleteFile(String fileName) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build());
    }
    public void getFile(String fileName){

    }
}
