package com.easylead.easylead.domain.request.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Progress {
    P0("접수 완료"),
    P1("담당자 확인 중"),
    P2("글맞춤 중"),
    P3("검수 중"),
    P4("글맞춤 완료");

    private final String description;
}
