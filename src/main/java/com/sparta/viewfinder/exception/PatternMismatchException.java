package com.sparta.viewfinder.exception;

import com.sparta.viewfinder.dto.common.CustomResponseCode;
import lombok.Getter;

@Getter
public class PatternMismatchException extends CustomException {
    public PatternMismatchException(CustomResponseCode code) {
        super(code);
    }
}