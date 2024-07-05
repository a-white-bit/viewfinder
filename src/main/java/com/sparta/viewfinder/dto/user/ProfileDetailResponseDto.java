package com.sparta.viewfinder.dto.user;

import com.sparta.viewfinder.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileDetailResponseDto {
    private Long userId;
    private String name;
    private String headline;
    private String email;
    private String phoneNumber;
    private String sns;


    public ProfileDetailResponseDto(Profile profile) {
        this.userId = profile.getId();
        this.name = profile.getUser().getName();
        this.headline = profile.getHeadline();
        this.email = profile.getUser().getEmail();
        this.phoneNumber = profile.getPhoneNumber();
        this.sns = profile.getSns();
    }
}