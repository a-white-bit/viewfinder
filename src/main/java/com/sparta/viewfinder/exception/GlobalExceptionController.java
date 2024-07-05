package com.sparta.viewfinder.exception;

import com.sparta.viewfinder.dto.common.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler {

    // CustomException 예외 처리
    @ExceptionHandler(CustomException.class)
    public ErrorResponseDto handleCustomException(CustomException e) {
        return createCustomErrorDto(e);
    }

    // MethodArgumentNotValidException 예외처리
    /*@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.warn("Method Argument Not Valid Exception");
        return handleExceptionInternal(errorCode, e);
    }*/

    // 그 외 예외처리들
    @ExceptionHandler(Exception.class)
    public ErrorResponseDto handleAllException(Exception e) {
        log.warn("Unhandled Exception: {}", e.getMessage());

        return new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                e.getMessage());
    }

    // 공통 예외 처리 메서드
    private ErrorResponseDto createCustomErrorDto(CustomException e) {
        log.warn("{} Exception: {}", e.getClass().getSimpleName(), e.getMessage());

        int httpStatusCode = e.getCode().getHttpStatusCode();
        String httpStatus = e.getCode().name();
        String message = e.getCode().getCustomMessage();

        return new ErrorResponseDto(httpStatusCode, httpStatus, message);
    }
}