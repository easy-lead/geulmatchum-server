package com.easylead.easylead.domain.text.service;

import com.easylead.easylead.common.error.ErrorCode;
import com.easylead.easylead.common.exception.ApiException;
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
    private final GoogleStorageService googleStorageService;

    public String detectTextPDF(MultipartFile file){
        String gcsSourcePath = googleStorageService.getGcsSourcePath(file);
        log.info("=========gcsSourcePath : "+gcsSourcePath+"==============");
        String gcsDestinationPath = googleStorageService.getGcsDestinationPath(file.getOriginalFilename());

        return null;
    }

    public String detectTextImage(MultipartFile file) {
        String imgUrl = s3Service.uploadImage(file);
        return googleVisionService.detechString(imgUrl);
    }
}
