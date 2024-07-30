package com.easylead.easylead.domain.text.service;

import com.easylead.easylead.common.error.ErrorCode;
import com.easylead.easylead.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TextService {
    private final S3Service s3Service;
    private final GoogleVisionService googleVisionService;
    private final GoogleStorageService googleStorageService;

    public List<String> detectTextPDF(MultipartFile file) throws Exception {
        String gcsSourcePath = googleStorageService.getGcsSourcePath(file);
        log.info("=========gcsSourcePath : "+gcsSourcePath+"==============");
        String gcsDestinationPath = googleStorageService.getGcsDestinationPath(file.getOriginalFilename());
        List<String> reqtext = googleVisionService.detectDocumentsGcs(gcsSourcePath,gcsDestinationPath);

        return reqtext;
    }

    public String detectTextImage(MultipartFile file) {
        String imgUrl = s3Service.uploadImage(file);
        return googleVisionService.detechStringImage(imgUrl);
    }
}
