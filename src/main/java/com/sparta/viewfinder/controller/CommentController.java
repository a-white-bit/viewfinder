package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.CommentRequestDto;
import com.sparta.viewfinder.dto.CommentResponseDto;
import com.sparta.viewfinder.security.UserDetailsImpl;
import com.sparta.viewfinder.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

  private final CommentService commentService;
  private static final String DELETE_COMMENT_SUCCESS_MESSAGE = "댓글이 삭제되었습니다";
  private static final String NOT_FOUND_COMMENT = "댓글을 찾을 수 없습니다";

  // 댓글 생성
  @PostMapping
  public ResponseEntity<CommentResponseDto> createComment(
        @RequestParam Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody CommentRequestDto requestDto) {
    CommentResponseDto res = commentService.createComment(userDetails, postId, requestDto);
    return ResponseEntity.ok(res);
  }

  // 게시물의 댓글 조회
  @GetMapping
  public ResponseEntity<Object> readComment(@RequestParam Long postId) {
    List<CommentResponseDto> res = commentService.readComment(postId);
    if (res.isEmpty()) {
      return ResponseEntity.ok().body(NOT_FOUND_COMMENT);
    } else {
      return ResponseEntity.ok(res);
    }
  }

  // 댓글 수정
  @PatchMapping("/{id}")
  public ResponseEntity<CommentResponseDto> updateComment(
        @PathVariable Long id,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody CommentRequestDto requestDto) {
    CommentResponseDto res = commentService.updateComment(userDetails, id, requestDto);
    return ResponseEntity.ok(res);
  }

  // 댓글 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteComment(
        @PathVariable Long id,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
    commentService.deleteComment(id, userDetails);
    return ResponseEntity.ok().body(DELETE_COMMENT_SUCCESS_MESSAGE);
  }
}