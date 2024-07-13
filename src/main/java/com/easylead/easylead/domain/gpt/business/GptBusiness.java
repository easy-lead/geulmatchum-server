package com.easylead.easylead.domain.gpt.business;

import com.easylead.easylead.common.annotation.Business;
import com.easylead.easylead.domain.gpt.convertor.GptConverter;
import com.easylead.easylead.domain.gpt.dto.ResponseDTO;
import com.easylead.easylead.domain.gpt.service.GptService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;

import java.net.http.HttpRequest;

@Business
@RequiredArgsConstructor
public class GptBusiness {
    private final GptService gptService;
    private final GptConverter gptConverter;
    public ResponseDTO getEasyToRead(String text) throws JsonProcessingException {

        HttpRequest request = gptService.requestGPT(text);
        return gptConverter.toResponseDTO(gptService.responseGPT(request));
    }


}
