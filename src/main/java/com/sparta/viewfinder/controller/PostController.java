package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.post.PostReadResponseDto;
import com.sparta.viewfinder.dto.post.PostRequestDto;
import com.sparta.viewfinder.dto.post.PostResponseDto;
import com.sparta.viewfinder.dto.common.CustomResponseCode;
import com.sparta.viewfinder.dto.common.SuccessResponseDto;
import com.sparta.viewfinder.security.UserDetailsImpl;
import com.sparta.viewfinder.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService service;

    @PostMapping("/posts")
    public SuccessResponseDto<PostResponseDto> createPost(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody PostRequestDto requestDto) {
        PostResponseDto res = service.createPost(userDetails, requestDto);
        return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, res);
    }

    @GetMapping("/posts")
    public SuccessResponseDto<Page<PostResponseDto>> readAllPost(@RequestParam int page) {
        Page<PostResponseDto> res = service.readAllPost(page-1);
        return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, res);
    }

    @GetMapping("/posts/{id}")
    public SuccessResponseDto<PostReadResponseDto> readPost(@PathVariable Long id) {
        PostReadResponseDto res = service.readPost(id);
        return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, res);
    }

    @PatchMapping("/posts/{id}")
    public SuccessResponseDto<PostResponseDto> updatePost(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody PostRequestDto requestDto) {
        PostResponseDto res = service.updatePost(id, userDetails, requestDto);
        return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, res);
    }

    @DeleteMapping("/posts/{id}")
    public SuccessResponseDto<String> deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        service.deletePost(id, userDetails);
        return new SuccessResponseDto<>(CustomResponseCode.SUCCESS, null);
    }
}
