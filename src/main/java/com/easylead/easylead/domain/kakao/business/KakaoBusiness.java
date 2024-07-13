package com.easylead.easylead.domain.kakao.business;

import com.easylead.easylead.common.annotation.Business;
import com.easylead.easylead.domain.kakao.converter.KarloConverter;
import com.easylead.easylead.domain.kakao.dto.KarloResponseDTO;
import com.easylead.easylead.domain.kakao.service.KakaoService;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class KakaoBusiness {

    private final KakaoService kakaoService;
    private final KarloConverter karloConverter;
    public KarloResponseDTO makeImage(String keyword){
        return karloConverter.toKarloResponseDTO(kakaoService.karlo(keyword));
    }

}
