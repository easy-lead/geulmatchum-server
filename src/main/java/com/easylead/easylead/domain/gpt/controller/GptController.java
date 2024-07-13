package com.easylead.easylead.domain.gpt.controller;

import com.easylead.easylead.domain.gpt.business.GptBusiness;
import com.easylead.easylead.domain.gpt.dto.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/gpt")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GptController {
    private final GptBusiness gptBusiness;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> easyToReadShort(@RequestParam String text) throws JsonProcessingException {
        return ResponseEntity.ok(gptBusiness.getEasyToRead(text));
    }


}
