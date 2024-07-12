package com.easylead.easylead.domain.gpt.controller;

import com.easylead.easylead.domain.gpt.business.GptBusiness;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/gpt")
public class GptController {
    private final GptBusiness gptBusiness;

    @GetMapping("")
    public ResponseEntity<String> easyToReadShort(@RequestParam String text) throws JsonProcessingException {
        return ResponseEntity.ok(gptBusiness.getEasyToRead(text));
    }


}
