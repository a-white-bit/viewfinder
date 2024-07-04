package com.sparta.viewfinder.dto;

import com.sparta.viewfinder.entity.Profile;
import lombok.Getter;

@Getter
public class ProfileUpdateResponseDto {
    private Long userId;
    private String headline;
    private String phoneNumber;
    private String sns;
    private String createAt;
    private String modifiedAt;


    public ProfileUpdateResponseDto(Profile profile) {
        this.userId = profile.getId();
        this.headline = profile.getHeadline();
        this.phoneNumber = profile.getPhoneNumber();
        this.sns = profile.getSns();
        this.createAt = profile.getCreatedAt();
        this.modifiedAt = profile.getModifiedAt();
    }
}
