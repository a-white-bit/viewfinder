package com.sparta.viewfinder.dto;

import com.sparta.viewfinder.entity.Comment;
import lombok.Getter;

@Getter

public class CommentResponseDto {
    private Long userId;
    private String username;
    private Long postId;
    private String content;
    private String createAt;
    private String modifiedAt;


    public CommentResponseDto(Comment comment){
        this.userId = comment.getUser().getId();
        this.username = comment.getUser().getUsername();
        this.postId = comment.getPost().getId();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}