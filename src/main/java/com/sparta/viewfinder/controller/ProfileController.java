package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.user.ProfileAllResponseDto;
import com.sparta.viewfinder.dto.user.ProfileDetailResponseDto;
import com.sparta.viewfinder.dto.user.ProfileUpdateRequestDto;
import com.sparta.viewfinder.dto.user.ProfileUpdateResponseDto;
import com.sparta.viewfinder.dto.common.CustomResponseCode;
import com.sparta.viewfinder.dto.common.SuccessResponseDto;
import com.sparta.viewfinder.security.UserDetailsImpl;
import com.sparta.viewfinder.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/profile")
    public SuccessResponseDto<List<ProfileAllResponseDto>> getAllProfiles() {
        List<ProfileAllResponseDto> res = profileService.getAllProfiles();
        return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, res);
    }

    @GetMapping("/profile/{profile_id}")
    public SuccessResponseDto<ProfileDetailResponseDto> getProfileDetail(@PathVariable("profile_id") Long profileId) {
        ProfileDetailResponseDto res = profileService.getProfileDetail(profileId);
        return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, res);
    }

    @PatchMapping("/profile")
    public SuccessResponseDto<ProfileUpdateResponseDto> updateProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ProfileUpdateRequestDto requestDto) {
        ProfileUpdateResponseDto res = profileService.updateProfile(userDetails, requestDto);
        return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, res);
    }
}
