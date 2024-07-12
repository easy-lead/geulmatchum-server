package com.easylead.easylead.domain.kakao.controller;

import com.easylead.easylead.domain.kakao.business.KakaoBusiness;
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
@RequestMapping("/karlo")
public class kakaoController {

    private final KakaoBusiness kakaoBusiness;

    @GetMapping("")
    public ResponseEntity<String> makeImage(@RequestParam String keyword){
        return ResponseEntity.ok(kakaoBusiness.makeImage(keyword));
    }
}
