package com.sparta.viewfinder.dto;

import com.sparta.viewfinder.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long userId;
    private String username;
    private String content;
    private String createdAt;
    private String modifiedAt;

    public PostResponseDto(Post post) {
        this.userId = post.getUser().getId();
        this.username = post.getUser().getUsername();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
