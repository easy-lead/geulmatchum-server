package com.easylead.easylead.domain.request.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RequestReqDTO {
    private Long userId;
    private String ISBN;
}
