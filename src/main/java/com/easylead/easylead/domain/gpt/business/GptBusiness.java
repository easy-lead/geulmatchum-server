package com.easylead.easylead.domain.gpt.business;

import com.easylead.easylead.common.annotation.Business;
import com.easylead.easylead.domain.gpt.converter.GptConverter;
import com.easylead.easylead.domain.gpt.dto.ResponseDTO;
import com.easylead.easylead.domain.gpt.service.GptService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

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


    public Flux<String> ask(String text) throws JsonProcessingException {
        return gptService.askStream(text);
    }

    public Flux<String> getEasyToReadCustom(String text) throws JsonProcessingException {
        return gptService.askCustomStream(text);
    }

    public ResponseDTO getImage(String keyword) throws JsonProcessingException {
        HttpRequest requestImgPrompt = gptService.requestImgPrompt(keyword);
        String reqText = gptService.responseGPT(requestImgPrompt);
        System.out.println("==================reqText : "+reqText);
        HttpRequest request = gptService.requestGPTImage(reqText);
        return gptConverter.toResponseDTO(gptService.responseDalle(request));
    }

}
