package com.easylead.easylead.domain.gpt.service;

import com.easylead.easylead.common.error.ErrorCode;
import com.easylead.easylead.common.exception.ApiException;
import com.easylead.easylead.config.GptConfig;
import com.easylead.easylead.domain.gpt.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GptService {
    @Value("${gpt.api_key}")

    private String gptApiKey;

    @Value("${gpt.api_key_custom}")
    private String gptApiCustomkey;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE );


    public HttpRequest requestGPT(String text) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> messages = new ArrayList<>();
        // Assistant API 사용할지 Prompt를 변경할지 선택하기
        // 시스템 역할 설정
        messages.add(new Message("너는 발달장애인을 위한 읽기 쉬운책을 만들 수 있는 전문가야." +
                "주어진 글을 아래와 같은 조건에 맞게 수정해줘. " +
                "-누가 말했는지 이름을 표시\n" +
                "-복합 문장 단순화(한 문장에 한 개의 내용만 있는것)\n" +
                "- 수동형을 능동형으로 바꿔 사용\n" +
                "- 미사여구(부사,형용사 등) 최소화\n" +
                "- 장문을 단문으로\n" +
                "- 이중 부정어 최소화\n" +
                "- 서술어를 붙여 문장을 완성 \n" +
                "- 접속어 삭제 ", "system"));
        // 사용자의 질문
        messages.add(new Message("아래 글을 발달장애인이 읽을 수 있게 읽기 쉬운 책으로 만들어줘.\n"+"그 새 아주머니와 선생님은 복도로 나가 이야기를 나누고 있었습니다. \n" +
                "\"야, 쟤 1학년 때 5반이었지?\"\n" +
                "석우는 옆에 앉은 서경이에게 물었습니다. 서경이는 석우네 이웃 동네에 사는 아이입니다. \n" +
                "\"응, 공부는 잘하는데 친구가 별로 없대.\"\n" +
                "조금 뒤 선생님이 들어왔습니다. 그러고는 아이들을 둘러보며 말했습니다. \n" +
                "\"혹시 집이 제일교회에서 가까운 사람?\"\n" +
                "제일 교회는 동네에서 가장 큰 교회입니다. 석우네 집에서 50미터쯤 떨어져 있지요. \n" +
                "\"저,저요.\"", "user"));
        // 어시스턴스
        messages.add(new Message("아주머니와 선생님은 복도에서 대화를 했습니다.\n" +
                "\n" +
                "석우 : 야, 영택이가 1학년 때 5반이었지? \n" +
                "\n" +
                "석우가 서경이에게 물었습니다.\n" +
                "서경이는 석우와 이웃 동네에 사는 친구입니다.\n" +
                "\n" +
                "서경 : 응, 영택이는 공부를 잘한대. 하지만 영택이는 친구가 없대. \n" +
                "\n" +
                "선생님이 들어왔습니다.\n" +
                "아이들을 보면서 말했습니다.\n" +
                "\n" +
                "선생님 : 집이 제일교회랑 가까운 사람이 누구니 ? \n" +
                "\n" +
                "제일 교회는 가장 큰 교회입니다.\n" +
                "\n" +
                "석우 집에서 가깝습니다.\n" +
                "\n" +
                "석우 : 저요", "assistant"));
        messages.add(new Message("아래 글을 발달장애인이 읽을 수 있게 읽기 쉬운 책으로 만들어줘.\n"+text, "user"));
        // temperature 는 답변의 창의성을 나타냄 온도가 낮을수록 정보성의 글
        ChatGPTRequestDTO chatGptRequest = new ChatGPTRequestDTO("gpt-4", messages, 0.3,false);
        String input = null;
        input = mapper.writeValueAsString(chatGptRequest);
        System.out.println(input);
        System.out.println("apikey : " + gptApiKey);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + gptApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(input))
                .build();

        return request;

    }
    public String responseGPT(HttpRequest request) throws JsonProcessingException {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.body());
        ObjectMapper objectMapper = new ObjectMapper();
        ChatGPTResponseDTO chatGPTResponseDTO = objectMapper.readValue(response.body(), ChatGPTResponseDTO.class);
        if(chatGPTResponseDTO.getChoices() ==null){
            throw new ApiException(ErrorCode.SERVER_ERROR);
        }
        List<Choice> choices = chatGPTResponseDTO.getChoices();

        String content = choices.get(0).getMessage().getContent();
        String subject = "";
        try {
            String[] splitContent = content.split("\n");
            subject = splitContent[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("subject = " + subject);
        System.out.println("content = " + content);
        return content;
    }

    public Flux<String> askStream(String text) throws JsonProcessingException {

        WebClient client = WebClient.builder()
                .baseUrl(GptConfig.CHAT_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(GptConfig.AUTHORIZATION, GptConfig.BEARER + gptApiKey)
                .build();

        List<Message> messages = new ArrayList<>();
        // 시스템 역할 설정
        messages.add(new Message("너는 발달장애인을 위한 읽기 쉬운책을 만들 수 있는 전문가야." +
                "주어진 글을 아래와 같은 조건에 맞게 수정해줘. " +
                "-누가 말했는지 이름을 표시\n" +
                "-복합 문장 단순화(한 문장에 한 개의 내용만 있는것)\n" +
                "- 수동형을 능동형으로 바꿔 사용\n" +
                "- 미사여구(부사,형용사 등) 최소화\n" +
                "- 장문을 단문으로\n" +
                "- 이중 부정어 최소화\n" +
                "- 서술어를 붙여 문장을 완성 \n" +
                "- 접속어 삭제 ", "system"));
        // 사용자의 질문
        messages.add(new Message("아래 글을 발달장애인이 읽을 수 있게 읽기 쉬운 책으로 만들어줘.\n"+"그 새 아주머니와 선생님은 복도로 나가 이야기를 나누고 있었습니다. \n" +
                "\"야, 쟤 1학년 때 5반이었지?\"\n" +
                "석우는 옆에 앉은 서경이에게 물었습니다. 서경이는 석우네 이웃 동네에 사는 아이입니다. \n" +
                "\"응, 공부는 잘하는데 친구가 별로 없대.\"\n" +
                "조금 뒤 선생님이 들어왔습니다. 그러고는 아이들을 둘러보며 말했습니다. \n" +
                "\"혹시 집이 제일교회에서 가까운 사람?\"\n" +
                "제일 교회는 동네에서 가장 큰 교회입니다. 석우네 집에서 50미터쯤 떨어져 있지요. \n" +
                "\"저,저요.\"", "user"));
        // 어시스턴스
        messages.add(new Message("아주머니와 선생님은 복도에서 대화를 했습니다.\n" +
                "\n" +
                "석우 : 야, 영택이가 1학년 때 5반이었지? \n" +
                "\n" +
                "석우가 서경이에게 물었습니다.\n" +
                "서경이는 석우와 이웃 동네에 사는 친구입니다.\n" +
                "\n" +
                "서경 : 응, 영택이는 공부를 잘한대. 하지만 영택이는 친구가 없대. \n" +
                "\n" +
                "선생님이 들어왔습니다.\n" +
                "아이들을 보면서 말했습니다.\n" +
                "\n" +
                "선생님 : 집이 제일교회랑 가까운 사람이 누구니 ? \n" +
                "\n" +
                "제일 교회는 가장 큰 교회입니다.\n" +
                "\n" +
                "석우 집에서 가깝습니다.\n" +
                "\n" +
                "석우 : 저요", "assistant"));
        messages.add(new Message("아래 글을 발달장애인이 읽을 수 있게 읽기 쉬운 책으로 만들어줘.\n"+text, "user"));
        ChatGPTRequestDTO chatGptRequest = new ChatGPTRequestDTO(
                GptConfig.CHAT_MODEL,
                messages,
                GptConfig.TEMPERATURE,
                GptConfig.STREAM
        );
        String requestValue = objectMapper.writeValueAsString(chatGptRequest);

        Flux<String> eventStream = client.post()
                .bodyValue(requestValue)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class);
        return eventStream;
    }

    public HttpRequest requestGPTCustom(String text) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> messages = new ArrayList<>();
        // Assistant API 사용할지 Prompt를 변경할지 선택하기
        // 시스템 역할 설정
        messages.add(new Message(text, "user"));

        ChatGPTRequestDTO chatGptRequest = new ChatGPTRequestDTO("ft:gpt-3.5-turbo-0125:personal::9klL6p0E", messages, 0.3,false);
        String input = null;
        input = mapper.writeValueAsString(chatGptRequest);
        System.out.println(input);
        System.out.println("apikey : " + gptApiCustomkey);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openai.com/v1/chat/completions"))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + gptApiCustomkey)
            .POST(HttpRequest.BodyPublishers.ofString(input))
            .build();

        return request;


    }
}
