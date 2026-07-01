package com.ecommerce.app_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    @Autowired
    private S3Client s3Client;

    @Value("${supabase.storage.bucket-name}")
    private String bucketName;

    @Value("${supabase.storage.endpoint}")
    private String storageEndpoint;

    public String uploadImage(MultipartFile file) throws IOException {

        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf(".")) : ".jpg";
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(uniqueFileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        return String.format("https://wdoxdxvsyrbhsxoxqzed.supabase.co/storage/v1/object/public/%s/%s", bucketName, uniqueFileName);
    }

    public void deleteImageFromSupabase(String imageUrl) {
        String defaultImageName = "default.png";

        if (imageUrl != null && imageUrl.contains("supabase.co") && !imageUrl.contains(defaultImageName)) {

            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build());
        }
    }


}