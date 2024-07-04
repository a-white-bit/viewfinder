package com.sparta.viewfinder.security;

import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.entity.UserStatusEnum;
import com.sparta.viewfinder.exception.MismatchException;
import com.sparta.viewfinder.exception.NotFoundException;
import com.sparta.viewfinder.exception.UserErrorCode;
import com.sparta.viewfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NotFoundException(UserErrorCode.USER_NOT_FOUND));
    if (user.getStatus() == UserStatusEnum.NON_USER) {
      throw new MismatchException(UserErrorCode.WITHDRAW_USER);
    }

    return new UserDetailsImpl(user);
  }
}
