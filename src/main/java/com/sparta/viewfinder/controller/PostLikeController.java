package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.common.CustomResponseCode;
import com.sparta.viewfinder.dto.common.SuccessResponseDto;
import com.sparta.viewfinder.dto.post.PostResponseDto;
import com.sparta.viewfinder.security.UserDetailsImpl;
import com.sparta.viewfinder.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/posts/{postId}/like")
    public SuccessResponseDto<?> doLike(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postLikeService.doLike(postId, userDetails.getUser().getId());
        return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, null);
    }

    @DeleteMapping("/posts/{postId}/like")
    public SuccessResponseDto<?> undoLike(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postLikeService.undoLike(postId, userDetails.getUser().getId());
        return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, null);
    }

    // 사용자가 좋아요한 게시글 목록 조회
    @GetMapping("/posts/like")
    public SuccessResponseDto<Page<PostResponseDto>> getPosts(
            @RequestParam int page,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Page<PostResponseDto> res = postLikeService.getPosts(page-1, userDetails.getUser().getId());
        return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, res);
    }

}