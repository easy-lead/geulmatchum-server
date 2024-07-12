package com.easylead.easylead.domain.kakao.business;

import com.easylead.easylead.common.annotation.Business;
import com.easylead.easylead.domain.kakao.service.KakaoService;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class KakaoBusiness {

    private final KakaoService kakaoService;
    public String makeImage(String keyword){
        return kakaoService.karlo(keyword);
    }

}
