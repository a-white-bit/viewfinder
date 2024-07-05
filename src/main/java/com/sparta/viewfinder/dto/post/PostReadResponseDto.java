package com.sparta.viewfinder.dto.post;

import com.sparta.viewfinder.entity.Post;
import lombok.Getter;

@Getter
public class PostReadResponseDto {
    private final Long userId;
    private final String username;
    private final String content;
    private final Long likeCount;
    private final String createdAt;
    private final String modifiedAt;

    public PostReadResponseDto(Post post, Long likeCount) {
        this.userId = post.getUser().getId();
        this.username = post.getUser().getUsername();
        this.content = post.getContent();
        this.likeCount = likeCount;
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
