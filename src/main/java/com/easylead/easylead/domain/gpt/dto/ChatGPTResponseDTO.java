package com.easylead.easylead.domain.gpt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatGPTResponseDTO {
    String id;
    String object;
    String model;
    Usage usage;
    List<Choice> choices;
    String system_fingerprint;
}


