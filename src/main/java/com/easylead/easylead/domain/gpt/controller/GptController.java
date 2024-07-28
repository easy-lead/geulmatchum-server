package com.easylead.easylead.domain.gpt.controller;

import com.easylead.easylead.domain.gpt.business.GptBusiness;
import com.easylead.easylead.domain.gpt.dto.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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

    @GetMapping(value="/custom", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> easyLeadCustom(@RequestParam String text)
        {
        try {
            return gptBusiness.getEasyToReadCustom(text);
        }catch (JsonProcessingException je){
            log.error(je.getMessage());
            return Flux.empty();
        }

    }

    @GetMapping("/image")
    public ResponseEntity<ResponseDTO> ImageGenerate(@RequestParam String keyword)
        throws JsonProcessingException {
        return ResponseEntity.ok(gptBusiness.getImage(keyword));
    }


}
