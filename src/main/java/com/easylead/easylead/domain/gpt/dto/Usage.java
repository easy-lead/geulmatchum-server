package com.easylead.easylead.domain.gpt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Usage {
    @JsonProperty("prompt_tokens")
    String promptTokens;

    @JsonProperty("completion_tokens")
    String completionTokens;

    @JsonProperty("total_tokens")
    String totalTokens;

}
