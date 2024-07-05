package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.comment.CommentResponseDto;
import com.sparta.viewfinder.dto.common.CustomResponseCode;
import com.sparta.viewfinder.dto.common.SuccessResponseDto;
import com.sparta.viewfinder.dto.post.PostResponseDto;
import com.sparta.viewfinder.security.UserDetailsImpl;
import com.sparta.viewfinder.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/{postId}/comments/{commentId}/like")
    public SuccessResponseDto<?> doLike(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentLikeService.doLike(postId, commentId, userDetails.getUser().getId());
        return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, null);
    }

    @DeleteMapping("/{postId}/comments/{commentId}/like")
    public SuccessResponseDto<?> undoLike(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentLikeService.undoLike(postId, commentId, userDetails.getUser().getId());
        return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, null);
    }

    // 사용자가 좋아요한 게시글 댓글 목록 조회
    @GetMapping("/{postId}/comments/like")
    public SuccessResponseDto<Page<CommentResponseDto>> getComments(
            @RequestParam int page,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Page<CommentResponseDto> res = commentLikeService.getComments(page-1, userDetails.getUser().getId());
        return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, res);
    }
}