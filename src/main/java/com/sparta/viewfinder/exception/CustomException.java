package com.sparta.viewfinder.exception;

import com.sparta.viewfinder.dto.common.CustomResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class CustomException extends RuntimeException {
    protected final CustomResponseCode code;
}