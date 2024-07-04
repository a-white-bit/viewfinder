package com.sparta.viewfinder.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum EmailErrorCode implements ErrorCode {
  DUPLICATED_EMAIL(HttpStatus.CONFLICT,
      "중복된 e-mail입니다.");


  private final HttpStatus status;
  private final String message;

  @Override
  public HttpStatus getHttpStatus() {
    return this.status;
  }
}
