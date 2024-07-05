package com.sparta.viewfinder.security;

import com.sparta.viewfinder.dto.user.LoginRequestDto;
import com.sparta.viewfinder.dto.common.CustomResponseCode;
import com.sparta.viewfinder.entity.UserRoleEnum;
import com.sparta.viewfinder.entity.UserStatusEnum;
import com.sparta.viewfinder.exception.NotFoundException;
import com.sparta.viewfinder.jwt.JwtTokenHelper;
import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;


import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final JwtTokenHelper jwtTokenHelper;

  public JwtAuthenticationFilter(JwtTokenHelper jwtTokenHelper) {
    this.jwtTokenHelper = jwtTokenHelper;
    setFilterProcessesUrl("/login");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    try {
      LoginRequestDto requestDto = new ObjectMapper().readValue(
          request.getInputStream(), LoginRequestDto.class);

      return getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(
              requestDto.getUsername(),
              requestDto.getPassword(),
              null
          )
      );

    } catch (IOException e) {
      log.error(e.getMessage());
      throw new NotFoundException(CustomResponseCode.TOKEN_ERROR);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException {

    String username = ((UserDetailsImpl)authResult.getPrincipal()).getUsername();
    UserStatusEnum status = ((UserDetailsImpl)authResult.getPrincipal()).getUser().getStatus();
    UserRoleEnum role = ((UserDetailsImpl)authResult.getPrincipal()).getUser().getUserRole();

    log.info("username: {}", username);
    log.info("status: {}", status);
    log.info("role: {}", role);
    
    String accessToken = jwtTokenHelper.createToken(username, status, role);
    String refreshToken = jwtTokenHelper.createRefreshToken();
    response.addHeader(JwtTokenHelper.AUTHORIZATION_HEADER, accessToken);
    response.addHeader(JwtTokenHelper.REFRESH_TOKEN_HEADER, refreshToken);
    jwtTokenHelper.saveRefreshToken(username, refreshToken);

    response.setCharacterEncoding("utf-8");
    response.getWriter().write("상태 : " + response.getStatus() + ", 로그인 성공");
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed) throws IOException {

    response.setStatus(401);

    response.setCharacterEncoding("utf-8");
    response.getWriter().write("상태 : " + response.getStatus() + ", 로그인 실패");
  }
}