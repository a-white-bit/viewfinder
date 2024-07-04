package com.sparta.viewfinder.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,
            "해당 유저가 없습니다."),
    PW_MISMATCH(HttpStatus.NOT_FOUND,
        "유저이름과 비밀번호 불일치"),
    DUPLICATED_USER(HttpStatus.CONFLICT,
                    "중복된 유저입니다."),
    USER_NOT_MATCH(HttpStatus.NOT_FOUND,
            "유저가 일치하지 않습니다."),
    PATTERN_NOT_MATCH(HttpStatus.NOT_FOUND,
            "비밀번호 패턴이 일치하지 않습니다."),
    WITHDRAW_USER(HttpStatus.NOT_FOUND,
        "탈퇴한 회원입니다."),
    REFRESH_TOKEN_MISMATCH(HttpStatus.NOT_FOUND,
        "REFRESH_TOKEN 값이 일치 하지 않습니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST,
            "비밀번호가 일치하지 않습니다."),
    DUPLICATED_PASSWORD(HttpStatus.BAD_REQUEST,
            "현재 비밀번호와 같은 비밀번호로 수정할 수 없습니다."),
    DUPLICATED_PASSWORD_THREE_TIMES(HttpStatus.BAD_REQUEST,
            "새 비밀번호는 최근 이용한 비밀번호와 다르게 설정해야합니다."),
    NOT_ADMIN(HttpStatus.NOT_FOUND,
        "ADMIN TOKEN값이 일치하지 않습니다." );

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return this.status;
    }
}
