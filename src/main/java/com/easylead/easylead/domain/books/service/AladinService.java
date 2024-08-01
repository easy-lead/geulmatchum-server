package com.easylead.easylead.domain.books.service;

import com.easylead.easylead.common.error.ErrorCode;
import com.easylead.easylead.common.exception.ApiException;
import com.easylead.easylead.domain.books.dto.BookInfoResDTO;
import com.easylead.easylead.domain.books.dto.BookSearchResDTO;
import com.easylead.easylead.domain.gpt.dto.ChatGPTResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AladinService {
    @Value("${aladin.api_key}")

    private String aladinApiKey;
    public JsonNode search(String title, String author, String publisher) throws JsonProcessingException, UnsupportedEncodingException {
        StringBuilder queryBuilder = new StringBuilder();

        if(title !=null)
            queryBuilder.append(title).append(" ");

        if(author !=null)
            queryBuilder.append(author).append(" ");

        if(publisher !=null)
            queryBuilder.append(publisher).append(" ");

        String query = queryBuilder.toString();
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?TTBKey="+aladinApiKey+
                "&Query="+ encodedQuery +"&Output=JS&Cover=Big")).build();

        log.info(query);

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.body());
        // ObjectMapper 인스턴스 생성
        ObjectMapper objectMapper = new ObjectMapper();

        // JSON 문자열을 JsonNode로 변환
        JsonNode rootNode = objectMapper.readTree(response.body());

        // "item" 필드를 추출
        JsonNode itemNode = rootNode.path("item");

        return itemNode;
    }

    public BookInfoResDTO search(String isbn) throws JsonProcessingException {

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?TTBKey="+aladinApiKey+
                "&ItemId="+ isbn +"&Output=JS&Cover=Big")).build();


        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.body());

        // ObjectMapper 인스턴스 생성
        ObjectMapper objectMapper = new ObjectMapper();

        // JSON 문자열을 JsonNode로 변환
        JsonNode rootNode = objectMapper.readTree(response.body());

        // "item" 필드를 추출
        JsonNode itemNode = rootNode.path("item");

        BookInfoResDTO book = objectMapper.treeToValue(itemNode.get(0), BookInfoResDTO.class);
        return book;
    }
}