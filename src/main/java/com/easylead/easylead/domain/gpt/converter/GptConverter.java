package com.easylead.easylead.domain.gpt.converter;

import com.easylead.easylead.common.annotation.Converter;
import com.easylead.easylead.domain.gpt.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class GptConverter {
    public ResponseDTO toResponseDTO(String text){
        return ResponseDTO.builder()
                .response(text)
                .build();
    }
}
