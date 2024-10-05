package com.easylead.easylead.domain.gpt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    String content;
    String role;
}
