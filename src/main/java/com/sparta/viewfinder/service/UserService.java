package com.sparta.viewfinder.service;

import com.sparta.viewfinder.dto.SignUpRequestDto;
import com.sparta.viewfinder.dto.SignUpResponseDto;
import com.sparta.viewfinder.dto.UserUpdateRequestDto;
import com.sparta.viewfinder.entity.PasswordHistory;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.entity.UserRoleEnum;
import com.sparta.viewfinder.entity.UserStatusEnum;
import com.sparta.viewfinder.exception.*;
import com.sparta.viewfinder.repository.PasswordHistoryRepository;
import com.sparta.viewfinder.repository.UserRepository;
import com.sparta.viewfinder.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final PasswordHistoryRepository passwordHistoryRepository;

  @Value("${auth.admin_token}")
  private String ADMIN_TOKEN;

  public SignUpResponseDto signUp(SignUpRequestDto requestDto) {
    String username = requestDto.getUsername();
    String password = passwordEncoder.encode(requestDto.getPassword());

    // 회원 중복 확인
    if (userRepository.findByUsername(username).isPresent()) {
      throw new DuplicatedException(UserErrorCode.DUPLICATED_USER);
    }

    // email 중복확인
    String email = requestDto.getEmail();
    if(userRepository.findByEmail(email).isPresent()){
      throw new DuplicatedException(EmailErrorCode.DUPLICATED_EMAIL);
    }

    // 사용자 ROLE 확인
    UserRoleEnum role = UserRoleEnum.USER;
    if(requestDto.isAdmin()) {
        if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())){
          throw new MismatchException(UserErrorCode.NOT_ADMIN);
        }
        role = UserRoleEnum.ADMIN;
    }

    User user = new User(
        username,
        password,
        requestDto.getName(),
        email,
        role
    );
    userRepository.save(user);
    return new SignUpResponseDto(user);
  }

  // 회원 탈퇴
  @Transactional
  public void withDraw(Long id, String password) {
    User user = findById(id);
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new MismatchException(UserErrorCode.PW_MISMATCH);
    }
    if (user.getStatus().equals(UserStatusEnum.NON_USER)) {
      throw new NotFoundException(UserErrorCode.WITHDRAW_USER);
    }
    user.withDraw();
  }

  @Transactional
  public boolean logout(Long id) {
    User user = findById(id);
    return user.logout();
  }

  private User findById(Long id) {
    return userRepository.findById(id).orElseThrow(
        () -> new NotFoundException(UserErrorCode.USER_NOT_FOUND)
    );
  }

  public void refreshTokenCheck(String username, String refreshToken) {
    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new NotFoundException(UserErrorCode.USER_NOT_FOUND)
    );
    if (!user.getRefreshToken().equals(refreshToken)) {
      throw new MismatchException(UserErrorCode.REFRESH_TOKEN_MISMATCH);
    }
  }

  @Transactional
  public void updatePassword(UserDetailsImpl userDetails, UserUpdateRequestDto requestDto) {
    User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(() -> new NotFoundException(UserErrorCode.USER_NOT_FOUND));

    if(!passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword())){
      throw new MismatchException(UserErrorCode.WRONG_PASSWORD);
    }
    if(passwordEncoder.matches(requestDto.getNewPassword(), user.getPassword())){
      throw new DuplicatedException(UserErrorCode.DUPLICATED_PASSWORD);
    }

    List<PasswordHistory> recentPasswords = passwordHistoryRepository.findTop3ByUserIdOrderByCreatedAtDesc(user.getId());
    for (PasswordHistory passwordHistory : recentPasswords) {
      if(passwordEncoder.matches(requestDto.getNewPassword(), passwordHistory.getPassword())){
        throw new DuplicatedException(UserErrorCode.DUPLICATED_PASSWORD_THREE_TIMES);
      }
    }

    String encodedPassword = passwordEncoder.encode(requestDto.getNewPassword());
    PasswordHistory passwordHistory = new PasswordHistory(user, encodedPassword); // 비밀번호 변경 전, 사용중이던 비밀번호를 PasswordHistory에 저장부터 함
    passwordHistoryRepository.save(passwordHistory);

    user.updatePassword(encodedPassword);
  }

/*
  todo : 필터말고 controller로 작동하게끔 나중에 수정할 것(팀프로젝트 끝나고)
  public boolean login(LoginRequestDto requestDto) {
    User findUser = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(
        () -> new NotFoundException(UserErrorCode.USER_NOT_FOUND)
    );
    if (!findUser.getPassword().equals(requestDto.getPassword())) {
      throw new NotFoundException(UserErrorCode.USER_NOT_FOUND);
    }
    return true;
  }
 */
}
