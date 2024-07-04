package com.sparta.viewfinder.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,
            "댓글을 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return this.status;
    }
}
