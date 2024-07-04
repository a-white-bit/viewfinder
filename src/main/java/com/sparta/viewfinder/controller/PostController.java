package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.dto.PostRequestDto;
import com.sparta.viewfinder.dto.PostResponseDto;
import com.sparta.viewfinder.security.UserDetailsImpl;
import com.sparta.viewfinder.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService service;
    private static final String DELETE_POST = "게시글이 삭제 되었습니다.";

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody PostRequestDto requestDto) {
        PostResponseDto postResponseDto = service.createPost(userDetails, requestDto);
        return ResponseEntity.ok(postResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> readAllPost(@RequestParam int page) {
        Page<PostResponseDto> postResponseDtoList = service.readAllPost(page-1);
        return ResponseEntity.ok(postResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> readPost(@PathVariable Long id) {
        PostResponseDto postResponseDto = service.readPost(id);
        return ResponseEntity.ok(postResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody PostRequestDto requestDto) {
        PostResponseDto postResponseDto = service.updatePost(id, userDetails, requestDto);
        return ResponseEntity.ok(postResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        service.deletePost(id, userDetails);
        return ResponseEntity.ok().body(DELETE_POST);
    }
}
