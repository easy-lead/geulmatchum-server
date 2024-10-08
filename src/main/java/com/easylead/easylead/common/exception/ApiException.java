package com.easylead.easylead.common.exception;

import com.easylead.easylead.common.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException{

    private final ErrorCode errorCode;
    private final String errorDescription;

    public ApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorDescription = errorCode.getDescription();
    }

    public ApiException(ErrorCode errorCode, Throwable ex) {
        super(ex);
        this.errorCode = errorCode;
        this.errorDescription = errorCode.getDescription();
    }
}
