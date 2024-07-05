package com.sparta.viewfinder.dto.post;

import com.sparta.viewfinder.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private final Long userId;
    private final String username;
    private final String content;
    private final String createdAt;
    private final String modifiedAt;

    public PostResponseDto(Post post) {
        this.userId = post.getUser().getId();
        this.username = post.getUser().getUsername();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
