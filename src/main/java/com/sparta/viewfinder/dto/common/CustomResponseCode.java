package com.sparta.viewfinder.dto.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomResponseCode {
    SUCCESS(HttpStatus.OK.value(), "요청이 성공했습니다."),
    CREATED(HttpStatus.CREATED.value(), "리소스가 성공적으로 생성되었습니다."),
    INVALID_INPUT_VALUE(HttpStatus.METHOD_NOT_ALLOWED.value(), "유효하지 않은 입력 값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.NOT_FOUND.value(), "허용되지 않는 메서드입니다."),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "엔티티를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "내부 서버 오류입니다."),
    INVALID_TYPE_VALUE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "유효하지 않은 타입 값입니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "접근이 거부되었습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(),"인증이 필요합니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST.value(), "잘못된 파라미터 값입니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "데이터를 찾을 수 없습니다."),
    TOKEN_ERROR(HttpStatus.UNAUTHORIZED.value(), "잘못된 토큰값입니다."),

    // 좋아요
    DO_NOT_LIKE_MY_COMMENT(HttpStatus.METHOD_NOT_ALLOWED.value(), "자신의 댓글에 좋아요할 수 없습니다."),
    DO_NOT_LIKE_MY_POST(HttpStatus.METHOD_NOT_ALLOWED.value(), "자신의 게시물에 좋아요할 수 없습니다."),
    DUPLICATED_LIKE(HttpStatus.CONFLICT.value(), "중복된 좋아요입니다."),

    // 댓글, 게시글
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "댓글을 찾을 수 없습니다."),
    NOTHING_COMMENT(HttpStatus.OK.value(), "댓글이 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "게시글을 찾을 수 없습니다."),
    NOTHING_POST(HttpStatus.OK.value(), "게시글이 없습니다."),

    // 사용자 정보
    SUCCESS_LOGOUT(HttpStatus.OK.value(),"로그아웃 성공"),
    SUCCESS_SIGN_UP(HttpStatus.OK.value(),"회원가입에 성공하였습니다."),
    WITHDRAW_SUCCESS_MESSAGE(HttpStatus.OK.value(),"회원탈퇴에 성공했습니다."),
    REFRESH_TOKEN_SUCCESS_MESSAGE(HttpStatus.OK.value(),"토큰 재발급 성공했습니다."),
    SUCCESS_UPDATE_PASSWORD(HttpStatus.OK.value(),"비밀번호 변경이 정상적으로 처리되었습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "사용자를 찾을 수 없습니다."),
    FAIL_LOGOUT(HttpStatus.INTERNAL_SERVER_ERROR.value(), "로그아웃 실패"),
    PW_MISMATCH(HttpStatus.NOT_FOUND.value(), "잘못된 비밀번호입니다."),
    DUPLICATED_USER(HttpStatus.CONFLICT.value(), "중복된 사용자입니다."),
    USER_NOT_MATCH(HttpStatus.NOT_FOUND.value(), "사용자가 일치하지 않습니다."),
    PATTERN_NOT_MATCH(HttpStatus.NOT_FOUND.value(), "비밀번호 패턴이 일치하지 않습니다."),
    WITHDRAW_USER(HttpStatus.NOT_FOUND.value(), "이미 탈퇴한 회원입니다."),
    REFRESH_TOKEN_MISMATCH(HttpStatus.NOT_FOUND.value(), "잘못된 리프레시 토큰값입니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다."),
    DUPLICATED_PASSWORD(HttpStatus.BAD_REQUEST.value(), "현재 비밀번호와 같은 비밀번호로 수정할 수 없습니다."),
    DUPLICATED_PASSWORD_THREE_TIMES(HttpStatus.BAD_REQUEST.value(), "새 비밀번호는 최근 이용한 비밀번호와 다르게 설정해야합니다."),
    NOT_ADMIN(HttpStatus.NOT_FOUND.value(), "잘못된 관리자 토큰값입니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT.value(), "중복된 이메일입니다.");

    private final int httpStatusCode;
    private final String customMessage;
}