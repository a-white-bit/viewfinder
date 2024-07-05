package com.sparta.viewfinder.dto.user;

import com.sparta.viewfinder.entity.User;
import lombok.Getter;


//이름 변경 signUpResponseDto
@Getter
public class SignUpResponseDto {
    private String username;
    private String name;
    private String email;

    public SignUpResponseDto(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
