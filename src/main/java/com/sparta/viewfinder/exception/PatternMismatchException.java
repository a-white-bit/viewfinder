package com.sparta.viewfinder.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PatternMismatchException extends RuntimeException {
    private final ErrorCode errorCode;
}
