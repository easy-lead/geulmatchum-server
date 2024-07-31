package com.easylead.easylead.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    SERVER_ERROR("G500",HttpStatus.INTERNAL_SERVER_ERROR, "요청 수행 중 서버 에러 발생"),
    // S3 관련 에러 코드
    EMPTY_FILE_EXCEPTION("F500-1", HttpStatus.BAD_REQUEST, "빈 파일입니다."),
    NO_FILE_EXTENTION("F500-2", HttpStatus.BAD_REQUEST, "확장자가 없습니다."),
    INVALID_FILE_EXTENTION("F500-3", HttpStatus.BAD_REQUEST, "부적절한 확장자입니다."),
    IO_EXCEPTION_ON_IMAGE_UPLOAD("F502-1", HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드 중 에러가 발생했습니다."),
    IO_EXCEPTION_ON_FILE_UPLOAD("F502-1", HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 중 에러가 발생했습니다."),
    PUT_OBJECT_EXCEPTION("F502-2", HttpStatus.INTERNAL_SERVER_ERROR, "S3에 이미지 업로드 중 에러가 발생했습니다."),
    IO_EXCEPTION_ON_IMAGE_DELETE("F502-3", HttpStatus.INTERNAL_SERVER_ERROR, "이미지 삭제 중 에러가 발생했습니다."),

    USER_NOT_FOUND("U404", HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다.");
    private final String errorCode;
    private final HttpStatus httpStatusCode;
    private final String description;

}
