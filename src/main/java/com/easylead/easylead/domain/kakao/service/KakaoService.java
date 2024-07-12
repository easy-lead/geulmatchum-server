package com.easylead.easylead.domain.kakao.service;

import com.easylead.easylead.domain.kakao.dto.Image;
import com.easylead.easylead.domain.kakao.dto.ImageRequestDTO;
import com.easylead.easylead.domain.kakao.dto.ImageResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoService {
    @Value("${kakao.api_key}")
    private String kakaoApiKey;

    public String karlo(String keyword) {


        StringBuilder prompt = new StringBuilder();
        prompt.append(keyword);
        prompt.append("illustrations for the book");
        ObjectMapper mapper = new ObjectMapper();
        ImageRequestDTO imageRequestDTO = new ImageRequestDTO(prompt.toString(), "person");
        String input = null;
        try {
            input = mapper.writeValueAsString(imageRequestDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.kakaobrain.com/v2/inference/karlo/t2i"))
                .header("Content-Type", "application/json")
                .header("Authorization", "KakaoAK " + kakaoApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(input))
                .build();
        System.out.println("request : " + request);
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper1 = new ObjectMapper();
            ImageResponseDTO dto;
            dto = mapper1.readValue(response.body(), ImageResponseDTO.class);
            List<Image> result = dto.getImages();
            String url = result.get(0).getImage();
            System.out.println(url);
            return url;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
