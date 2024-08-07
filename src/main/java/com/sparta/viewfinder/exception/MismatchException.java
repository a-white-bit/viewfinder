package com.sparta.viewfinder.exception;

import com.sparta.viewfinder.dto.common.CustomResponseCode;
import lombok.Getter;

@Getter
public class MismatchException extends CustomException {
    public MismatchException(CustomResponseCode code) {
        super(code);
    }
}