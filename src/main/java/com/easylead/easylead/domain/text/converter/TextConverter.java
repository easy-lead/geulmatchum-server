package com.easylead.easylead.domain.text.converter;

import com.easylead.easylead.common.annotation.Converter;
import com.easylead.easylead.domain.text.dto.TextFileResDTO;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class TextConverter {
    public TextFileResDTO toTextFileResDTO(String resText) {
        return TextFileResDTO.builder()
                .text(resText)
                .build();
    }
}
