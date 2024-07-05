package com.sparta.viewfinder.dto.comment;

import com.sparta.viewfinder.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private final Long userId;
    private final String username;
    private final Long postId;
    private final String content;
    private final String createAt;
    private final String modifiedAt;

    public CommentResponseDto(Comment comment){
        this.userId = comment.getUser().getId();
        this.username = comment.getUser().getUsername();
        this.postId = comment.getPost().getId();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}