package com.sparta.viewfinder.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST,
            "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND,
            "Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,
            "Internal server error"),
    TOKEN_ERROR(HttpStatus.UNAUTHORIZED,
        "토큰 에러!");

    private final HttpStatus httpStatus;
    private final String message;
}
