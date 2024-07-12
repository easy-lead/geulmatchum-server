package com.easylead.easylead.domain.gpt.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class Message {
    String content;
    String role;
}
