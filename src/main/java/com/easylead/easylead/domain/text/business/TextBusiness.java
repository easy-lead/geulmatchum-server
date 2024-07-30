package com.easylead.easylead.domain.text.business;

import com.easylead.easylead.common.annotation.Business;
import com.easylead.easylead.common.error.ErrorCode;
import com.easylead.easylead.common.exception.ApiException;
import com.easylead.easylead.domain.gpt.service.GptService;
import com.easylead.easylead.domain.text.converter.TextConverter;
import com.easylead.easylead.domain.text.dto.TextFileResDTO;
import com.easylead.easylead.domain.text.service.TextService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpRequest;
import java.util.Objects;

@Business
@RequiredArgsConstructor
@Slf4j
public class TextBusiness {

    private final TextService textService;
    private final GptService gptService;
    private final TextConverter textConverter;


    public TextFileResDTO easyToRead(MultipartFile file) throws JsonProcessingException {
        if (file.isEmpty() || Objects.isNull(file.getOriginalFilename())) {
            throw new ApiException(ErrorCode.EMPTY_FILE_EXCEPTION);
        }
        String fileUrl = textService.uploadFile(file);

        log.info("=========== fileUrl : "+fileUrl+"============");

        String reqText = textService.detectTextImage(fileUrl);

        log.info("=========== reqText : "+reqText+"============");

        HttpRequest request = gptService.requestGPTCustom(reqText);

//        HttpRequest requestImgPrompt = gptService.requestImgPrompt(reqText);
//        String prompt = gptService.responseGPT(requestImgPrompt);
//        HttpRequest requestImg = gptService.requestGPTImage(prompt);
//        String imgUrl = gptService.responseDalle(requestImg);
        return textConverter.toTextFileResDTO(gptService.responseGPT(request),"imgUrl");
    }
}
