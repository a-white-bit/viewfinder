package com.sparta.viewfinder.dto.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
    private String oldPassword;

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{}|;:'\",.<>?/]+$", message = "비밀번호는 알파벳 대소문자, 숫자 및 특수문자만 포함할 수 있습니다.")
    @Size(min=8, max=15)
    private String newPassword;
}
