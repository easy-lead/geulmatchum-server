package com.easylead.easylead.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    SERVER_ERROR("G500",HttpStatus.INTERNAL_SERVER_ERROR, "요청 수행 중 서버 에러 발생");

    private final String errorCode;
    private final HttpStatus httpStatusCode;
    private final String description;

}
