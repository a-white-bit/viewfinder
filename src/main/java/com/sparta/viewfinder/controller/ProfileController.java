package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.ProfileAllResponseDto;
import com.sparta.viewfinder.dto.ProfileDetailResponseDto;
import com.sparta.viewfinder.dto.ProfileUpdateRequestDto;
import com.sparta.viewfinder.dto.ProfileUpdateResponseDto;
import com.sparta.viewfinder.security.UserDetailsImpl;
import com.sparta.viewfinder.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<List<ProfileAllResponseDto>> getAllProfiles() {
        List<ProfileAllResponseDto> allProfiles = profileService.getAllProfiles();
        return ResponseEntity.ok().body(allProfiles);
    }

    @GetMapping("/{profile_id}")
    public ResponseEntity<ProfileDetailResponseDto> getProfileDetail(@PathVariable("profile_id") Long profileId) {
        ProfileDetailResponseDto profileDetail = profileService.getProfileDetail(profileId);
        return ResponseEntity.ok().body(profileDetail);
    }

    @PatchMapping
    public ResponseEntity<ProfileUpdateResponseDto> updateProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ProfileUpdateRequestDto requestDto) {
        ProfileUpdateResponseDto profileUpdateResponseDto = profileService.updateProfile(userDetails, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(profileUpdateResponseDto);
    }
}
