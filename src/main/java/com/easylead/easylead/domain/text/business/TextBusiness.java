package com.easylead.easylead.domain.text.business;

import com.easylead.easylead.common.annotation.Business;
import com.easylead.easylead.common.error.ErrorCode;
import com.easylead.easylead.common.exception.ApiException;
import com.easylead.easylead.domain.gpt.service.GptService;
import com.easylead.easylead.domain.text.converter.TextConverter;
import com.easylead.easylead.domain.text.dto.TextFileResDTO;
import com.easylead.easylead.domain.text.service.GoogleVisionService;
import com.easylead.easylead.domain.text.service.S3Service;
import com.easylead.easylead.domain.text.service.TextService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.Objects;

@Business
@RequiredArgsConstructor
@Slf4j
public class TextBusiness {

    private final TextService textService;
    private final GptService gptService;
    private final S3Service s3Service;
    private final GoogleVisionService googleVisionService;
    private final TextConverter textConverter;


    public TextFileResDTO easyToRead(MultipartFile file) throws Exception {
        if (file.isEmpty() || Objects.isNull(file.getOriginalFilename())) {
            throw new ApiException(ErrorCode.EMPTY_FILE_EXCEPTION);
        }

        String reqText = textService.detectTextPDF(file);

        log.info("=========== reqText : "+reqText+"============");

        StringBuilder result = new StringBuilder();

            HttpRequest request = gptService.requestGPT(reqText,"gpt-4o-mini");
            result.append(gptService.responseGPT(request));

        return textConverter.toTextFileResDTO(result.toString());

    }
    public TextFileResDTO easyToReadImage(MultipartFile file) throws JsonProcessingException {
        if (file.isEmpty() || Objects.isNull(file.getOriginalFilename())) {
            throw new ApiException(ErrorCode.EMPTY_FILE_EXCEPTION);
        }

        String reqText = textService.detectTextImage(file);

        log.info("=========== reqText : "+reqText+"============");


        HttpRequest request =  gptService.requestGPT(reqText,"gpt-4o-mini");
        return TextFileResDTO.builder().text(gptService.responseGPT(request)).build();
    }
}
