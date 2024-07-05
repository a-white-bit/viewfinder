package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.user.SignUpRequestDto;
import com.sparta.viewfinder.dto.user.SignUpResponseDto;
import com.sparta.viewfinder.dto.user.UserUpdateRequestDto;
import com.sparta.viewfinder.dto.user.WithDrawUserRequestDto;
import com.sparta.viewfinder.dto.common.CustomResponseCode;
import com.sparta.viewfinder.dto.common.SuccessResponseDto;
import com.sparta.viewfinder.entity.UserRoleEnum;
import com.sparta.viewfinder.entity.UserStatusEnum;
import com.sparta.viewfinder.jwt.JwtTokenHelper;
import com.sparta.viewfinder.security.UserDetailsImpl;
import com.sparta.viewfinder.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenHelper jwtTokenHelper;

    // 회원가입
    @PostMapping("/sign-up")
    public SuccessResponseDto<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto requestDto) {
        SignUpResponseDto res = userService.signUp(requestDto);
        return new SuccessResponseDto<>(CustomResponseCode.SUCCESS_SIGN_UP, res);
    }

    @DeleteMapping("/withdraw")
    public SuccessResponseDto<?> withDraw(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody WithDrawUserRequestDto requestDto) {

        userService.withDraw(userDetails.getUser().getId(), requestDto.getPassword());
        return new SuccessResponseDto<>(CustomResponseCode.WITHDRAW_SUCCESS_MESSAGE, null);
    }

    @PostMapping("/log-out")
    public SuccessResponseDto<?> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userService.logout(userDetails.getUser().getId())) {
            return new SuccessResponseDto<>(CustomResponseCode.SUCCESS_LOGOUT, null);
        } else {
            return new SuccessResponseDto<>(CustomResponseCode.FAIL_LOGOUT, null);
        }
    }

    // 토큰 재발급
    @GetMapping("/refresh")
    public SuccessResponseDto<String> refresh(
            @RequestHeader(JwtTokenHelper.AUTHORIZATION_HEADER) String accessToken,
            @RequestHeader(JwtTokenHelper.REFRESH_TOKEN_HEADER) String refreshToken,
            HttpServletResponse response) {

        Claims claims = jwtTokenHelper.getExpiredAccessToken(accessToken);
        String username = claims.getSubject();
        String status = claims.get("status").toString();
        String role = claims.get("auth").toString();

        UserStatusEnum statusEnum = UserStatusEnum.valueOf(status);
        UserRoleEnum roleEnum = UserRoleEnum.valueOf(role);

        userService.refreshTokenCheck(username, refreshToken);

        String newAccessToken = jwtTokenHelper.createToken(username, statusEnum, roleEnum);
        response.setHeader(JwtTokenHelper.AUTHORIZATION_HEADER, newAccessToken);

        return new SuccessResponseDto<>(CustomResponseCode.REFRESH_TOKEN_SUCCESS_MESSAGE, newAccessToken);
    }

    @PatchMapping("/user")
    public SuccessResponseDto<?> updatePassword(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody UserUpdateRequestDto requestDto) {
        userService.updatePassword(userDetails, requestDto);
        return new SuccessResponseDto<>(CustomResponseCode.SUCCESS_UPDATE_PASSWORD, null);
    }
}
