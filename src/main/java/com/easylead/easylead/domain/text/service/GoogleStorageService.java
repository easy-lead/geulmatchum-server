package com.easylead.easylead.domain.text.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoogleStorageService {
    private final Storage storage = StorageOptions.getDefaultInstance().getService();
    private final String bucketName = "geulmatchum-file";

    public Blob uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Blob blob = null;
        try {
            blob = storage.create(
                    Blob.newBuilder(bucketName, fileName).build(),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return blob; // Return the file's public URL
    }

    public String getGcsSourcePath(MultipartFile file) {
        // Upload the file and get its public URL
        Blob blob = uploadFile(file);
        // Convert the public URL to gcsSourcePath
        return "gs://" + blob.getBucket() + "/" + blob.getName();
    }

    public String getGcsDestinationPath(String fileName) {
        // Define the GCS path for saving results
        // For example, saving results to a directory named "results"
        return String.format("gs://%s/results/%s", bucketName, fileName);
    }

}
