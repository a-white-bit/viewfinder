package com.sparta.viewfinder.exception;

import com.sparta.viewfinder.dto.common.CustomResponseCode;
import lombok.Getter;

@Getter
public class DuplicatedException extends CustomException {
    public DuplicatedException(CustomResponseCode code) {
        super(code);
    }
}