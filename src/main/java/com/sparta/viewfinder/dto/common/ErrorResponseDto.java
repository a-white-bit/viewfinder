package com.sparta.viewfinder.dto.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponseDto extends CommonResponseDto<String> {
    public ErrorResponseDto(int httpStatusCode, String httpStatus, String errorMessage) {
        setError(httpStatusCode, httpStatus, errorMessage);
    }

    @Override
    protected void setError(int httpStatusCode, String httpStatus, String errorMessage) {
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.customMessage = errorMessage;
    }

    @Override
    protected void setResponse(int httpStatusCode, String httpStatus, String customMessage, String data) {
        throw new UnsupportedOperationException("ErrorResponseDto does not support setResponse method");
    }
}