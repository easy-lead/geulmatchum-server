package com.easylead.easylead.domain.text.controller;

import com.easylead.easylead.domain.text.business.TextBusiness;
import com.easylead.easylead.domain.text.dto.TextFileResDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/text")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TextController {
    private final TextBusiness textBusiness;

    @PostMapping(value = "/image", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> easyToReadImage(Locale locale,
                                        HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestPart(value = "image") MultipartFile file) throws JsonProcessingException {
        try{
            return textBusiness.easyToReadImage(file);

        }catch (JsonProcessingException je){
            log.error(je.getMessage());
            return Flux.empty();
        }
    }

    @PostMapping("/file")
    public ResponseEntity<TextFileResDTO> easyToReadFile(@RequestPart(value = "file") MultipartFile file) throws JsonProcessingException {
        return ResponseEntity.ok(textBusiness.easyToRead(file));
    }

}
