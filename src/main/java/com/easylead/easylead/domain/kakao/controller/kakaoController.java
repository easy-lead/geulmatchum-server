package com.easylead.easylead.domain.kakao.controller;

import com.easylead.easylead.domain.kakao.business.KakaoBusiness;
import com.easylead.easylead.domain.kakao.dto.KarloResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/karlo")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class kakaoController {

    private final KakaoBusiness kakaoBusiness;

    @GetMapping("")
    public ResponseEntity<KarloResponseDTO> makeImage(@RequestParam String keyword){
        return ResponseEntity.ok(kakaoBusiness.makeImage(keyword));
    }
}
