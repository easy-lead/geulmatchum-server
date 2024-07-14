package com.easylead.easylead.domain.gpt.controller;

import com.easylead.easylead.domain.gpt.business.GptBusiness;
import com.easylead.easylead.domain.gpt.dto.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Locale;

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

    @GetMapping(value="/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> ask(Locale locale,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam String text){
        try {
            return gptBusiness.ask(text);
        }catch (JsonProcessingException je){
            log.error(je.getMessage());
            return Flux.empty();
        }
    }

    @GetMapping("/custom")
    public ResponseEntity<ResponseDTO> easyLeadCustom(@RequestParam String text)
        throws JsonProcessingException {
        return ResponseEntity.ok(gptBusiness.getEasyToReadCustom(text));

    }


}
