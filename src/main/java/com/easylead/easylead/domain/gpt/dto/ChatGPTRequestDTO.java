package com.easylead.easylead.domain.gpt.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonPropertyOrder({"model", "Message", "temperature","stream"})
public class ChatGPTRequestDTO {
    String model;
    List<Message> messages;
    double temperature;
    boolean stream;


}