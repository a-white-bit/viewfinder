package com.sparta.viewfinder.exception;

import com.sparta.viewfinder.dto.common.CustomResponseCode;
import lombok.Getter;

@Getter
public class NotFoundException extends CustomException {
    public NotFoundException(CustomResponseCode code) {
        super(code);
    }
}