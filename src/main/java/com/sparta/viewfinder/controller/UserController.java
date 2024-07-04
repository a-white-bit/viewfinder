package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.SignUpRequestDto;
import com.sparta.viewfinder.dto.UserUpdateRequestDto;
import com.sparta.viewfinder.dto.WithDrawUserRequestDto;
import com.sparta.viewfinder.entity.UserRoleEnum;
import com.sparta.viewfinder.entity.UserStatusEnum;
import com.sparta.viewfinder.jwt.JwtTokenHelper;
import com.sparta.viewfinder.security.UserDetailsImpl;
import com.sparta.viewfinder.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    private static final String SUCCESS_LOGIN = "로그인 성공";
    private static final String SUCCESS_LOGOUT = "로그아웃 성공";
    private static final String SUCCESS_SIGN_UP = "회원가입에 성공하였습니다.";
    private static final String WITHDRAW_SUCCESS_MESSAGE = "회원탈퇴에 성공했습니다.";
    private static final String REFRESH_TOKEN_SUCCESS_MESSAGE = "토큰 재발급 성공했습니다.";
    private static final String SUCCESS_UPDATE_PASSWORD = "비밀번호 변경이 정상적으로 처리되었습니다.";

    // 회원가입
    @PostMapping("/sign-up") // http://localhost:8080/sign-up POST
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequestDto requestDto) { // Valid를 달아야 검증 작동
        userService.signUp(requestDto);
        return ResponseEntity.ok().body(SUCCESS_SIGN_UP);
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<String> withDraw(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody WithDrawUserRequestDto requestDto) {

        userService.withDraw(userDetails.getUser().getId(), requestDto.getPassword());
        return ResponseEntity.ok().body(WITHDRAW_SUCCESS_MESSAGE);
    }

    @PostMapping("/log-out")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.logout(userDetails.getUser().getId());
        return ResponseEntity.ok().body(SUCCESS_LOGOUT);
    }

    // 토큰 재발급
    @GetMapping("/refresh")
    public ResponseEntity<String> refresh(
            @RequestHeader(JwtTokenHelper.AUTHORIZATION_HEADER) String accessToken,
            @RequestHeader(JwtTokenHelper.REFRESH_TOKEN_HEADER) String refreshToken) {

        Claims claims = jwtTokenHelper.getExpiredAccessToken(accessToken);
        String username = claims.getSubject();
        String status = claims.get("status").toString();
        String role = claims.get("auth").toString();

        UserStatusEnum statusEnum = UserStatusEnum.valueOf(status);
        UserRoleEnum roleEnum = UserRoleEnum.valueOf(role);

        userService.refreshTokenCheck(username, refreshToken);

        String newAccessToken = jwtTokenHelper.createToken(username, statusEnum, roleEnum);
        return ResponseEntity.ok()
            .header(JwtTokenHelper.AUTHORIZATION_HEADER, newAccessToken)
            .body(REFRESH_TOKEN_SUCCESS_MESSAGE + newAccessToken);
    }

    @PatchMapping("/user")
    public ResponseEntity<String> updatePassword(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody UserUpdateRequestDto requestDto) {
        userService.updatePassword(userDetails, requestDto);
        return ResponseEntity.ok().body(SUCCESS_UPDATE_PASSWORD);
    }
}
