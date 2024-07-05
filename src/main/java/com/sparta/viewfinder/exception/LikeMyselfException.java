package com.sparta.viewfinder.exception;

import com.sparta.viewfinder.dto.common.CustomResponseCode;
import lombok.Getter;

@Getter
public class LikeMyselfException extends CustomException {
    public LikeMyselfException(CustomResponseCode code) {
        super(code);
    }
}