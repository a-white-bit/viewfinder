package com.sparta.viewfinder.dto.common;

import lombok.Getter;

@Getter
public class SuccessResponseDto<T> extends CommonResponseDto<T> {
    public T data;

    public SuccessResponseDto(CustomResponseCode code, T data) {
        setResponse(code.getHttpStatusCode(), code.name(), code.getCustomMessage(), data);
    }

    @Override
    protected void setResponse(int httpStatusCode, String httpStatus, String customMessage, T data) {
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.customMessage = customMessage;
        this.data = data;
    }

    @Override
    protected void setError(int httpStatusCode, String httpStatus, String errorMessage) {
        throw new UnsupportedOperationException("SuccessResponseDto does not support setError method");
    }
}