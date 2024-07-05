package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.comment.CommentRequestDto;
import com.sparta.viewfinder.dto.comment.CommentResponseDto;
import com.sparta.viewfinder.dto.common.CustomResponseCode;
import com.sparta.viewfinder.dto.common.SuccessResponseDto;
import com.sparta.viewfinder.security.UserDetailsImpl;
import com.sparta.viewfinder.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class CommentController {

  private final CommentService commentService;

  // 댓글 생성
  @PostMapping("/{postId}/comments")
  public SuccessResponseDto<CommentResponseDto> createComment(
        @PathVariable Long postId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody CommentRequestDto requestDto) {
    CommentResponseDto res = commentService.createComment(userDetails, postId, requestDto);
    return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, res);
  }

  // 게시물의 댓글 조회
  @GetMapping("/{postId}/comments")
  public SuccessResponseDto<Object> readComment(@PathVariable Long postId) {
    List<CommentResponseDto> res = commentService.readComment(postId);
    if (res.isEmpty()) {
      return new SuccessResponseDto<>(CustomResponseCode.NOTHING_COMMENT, res);
    } else {
      return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, res);
    }
  }

  // 댓글 수정
  @PatchMapping("/{postId}/comments/{commentId}")
  public SuccessResponseDto<CommentResponseDto> updateComment(
        @PathVariable Long postId,
        @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody CommentRequestDto requestDto) {
    CommentResponseDto res = commentService.updateComment(userDetails, commentId, requestDto);
    return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, res);
  }

  // 댓글 삭제
  @DeleteMapping("/{postId}/comments/{commentId}")
  public SuccessResponseDto<?> deleteComment(
        @PathVariable Long postId,
        @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
    commentService.deleteComment(commentId, userDetails);
    return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, null);
  }
}