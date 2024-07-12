package com.easylead.easylead.domain.gpt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Choice {
    Integer index;
    Message message;

    @JsonProperty("finish_reason")
    String finishReason;
}