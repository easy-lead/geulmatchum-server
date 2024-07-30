package com.easylead.easylead.domain.text.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.easylead.easylead.common.error.ErrorCode;
import com.easylead.easylead.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;


    public String uploadImage(MultipartFile image) {
        this.validateImageFileExtention(image.getOriginalFilename());
        try {
            return this.uploadImageToS3(image);
        } catch (IOException e) {
            throw new ApiException(ErrorCode.IO_EXCEPTION_ON_IMAGE_UPLOAD);
        }
    }

    public String uploadPDF(MultipartFile file) {
        this.validatePDFExtention(file.getOriginalFilename());
        try {
            return this.uploadImageToS3(file);
        } catch (IOException e) {
            throw new ApiException(ErrorCode.IO_EXCEPTION_ON_FILE_UPLOAD);
        }
    }

    /**
     * 이미지 파일의 확장자 명이 올바른지 확인
     */
    private void validateImageFileExtention(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new ApiException(ErrorCode.NO_FILE_EXTENTION);
        }

        String extension = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtentionList.contains(extension)) {
            throw new ApiException(ErrorCode.INVALID_FILE_EXTENTION);
        }
    }

    /**
     * PDF 파일의 확장자 명이 올바른지 확인
     */
    private void validatePDFExtention(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new ApiException(ErrorCode.NO_FILE_EXTENTION);
        }

        String extension = filename.substring(lastDotIndex + 1).toLowerCase();
        if (!extension.equals("pdf")) {
            throw new ApiException(ErrorCode.INVALID_FILE_EXTENTION);
        }
    }

    /**
     * 실제 S3에 이미지 업로드하는 메서드
     */
    private String uploadImageToS3(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename(); //원본 파일 명
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")); //확장자 명

        String s3FileName =
                UUID.randomUUID().toString().substring(0, 10) + originalFilename; //실제 S3에 저장될 파일 명

        InputStream is = image.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is); // image를 byte 배열로 변환

        ObjectMetadata metadata = new ObjectMetadata(); // metadata 생성
        metadata.setContentType("image/" + extension);
        metadata.setContentLength(bytes.length);

        // S3에 요청할 때 사용할 byteInputStream 생성
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try {
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata);

            // 실제 S3에 이미지 업로드하는 코드
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.PUT_OBJECT_EXCEPTION, e);
        } finally {
            byteArrayInputStream.close();
            is.close();
        }

        return amazonS3.getUrl(bucketName, s3FileName).toString();
    }
}