package com.easylead.easylead.domain.text.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class TextService {
    private final S3Service s3Service;
    private final GoogleVisionService googleVisionService;

    public String detectTextImage(String imgUrl){
        return googleVisionService.detechString(imgUrl);
    }

    public String uploadFile(MultipartFile file) {
        return s3Service.uploadImage(file);
    }
}
