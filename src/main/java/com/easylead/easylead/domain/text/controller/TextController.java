package com.easylead.easylead.domain.text.controller;

import com.easylead.easylead.domain.text.business.TextBusiness;
import com.easylead.easylead.domain.text.dto.TextFileResDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/text")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TextController {
    private final TextBusiness textBusiness;

    @PostMapping("/multi")
    public ResponseEntity<TextFileResDTO> easyToReadFile(@RequestPart(value = "file") MultipartFile file) throws JsonProcessingException {
        return ResponseEntity.ok(textBusiness.easyToRead(file));
    }

}
