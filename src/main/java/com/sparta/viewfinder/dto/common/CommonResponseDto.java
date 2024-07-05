package com.sparta.viewfinder.dto.common;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class CommonResponseDto<T> {
    protected int httpStatusCode;
    protected String httpStatus;
    protected String customMessage;

    protected abstract void setResponse(int httpStatusCode, String httpStatus, String customMessage, T data);
    protected abstract void setError(int httpStatusCode, String httpStatus, String errorMessage);
}