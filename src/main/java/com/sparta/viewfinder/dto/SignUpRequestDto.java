package com.sparta.viewfinder.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
@Getter

//이름 변경 signUpRequestDto
public class SignUpRequestDto {
    @Size(min=4, max=10)
    @Pattern(regexp = "^[a-z0-9]+$", message = "영어 소문자와 숫자만 입력 가능합니다.")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{}|;:'\",.<>?/]+$", message = "비밀번호는 알파벳 대소문자, 숫자 및 특수문자만 포함할 수 있습니다.")
    @Size(min=8, max=15)
    private String password;

    private String name;

    @Email
    private String email;

    private boolean admin = false;
    private String adminToken = "";
}
