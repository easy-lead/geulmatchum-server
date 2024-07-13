package com.easylead.easylead.domain.kakao.converter;

import com.easylead.easylead.common.annotation.Converter;
import com.easylead.easylead.domain.kakao.dto.KarloResponseDTO;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class KarloConverter {

    public KarloResponseDTO toKarloResponseDTO (String url){
        return KarloResponseDTO.builder()
                .url(url)
                .build();
    }
}
