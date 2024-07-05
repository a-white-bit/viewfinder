package com.sparta.viewfinder.service;

import com.sparta.viewfinder.dto.common.CustomResponseCode;
import com.sparta.viewfinder.entity.Comment;
import com.sparta.viewfinder.entity.CommentLike;
import com.sparta.viewfinder.entity.CommentLikeId;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.exception.DuplicatedException;
import com.sparta.viewfinder.exception.LikeMyselfException;
import com.sparta.viewfinder.exception.NotFoundException;
import com.sparta.viewfinder.repository.CommentLikeRepository;
import com.sparta.viewfinder.repository.CommentRepository;
import com.sparta.viewfinder.repository.PostRepository;
import com.sparta.viewfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 댓글 좋아요 등록
    public void doLike(Long postId, Long commentId, Long userId) {
        // postId, commentId, userId가 유효한지 확인
        checkInputs(postId, commentId, userId);

        // 이미 해당 userId-commentId 로 좋아요 등록되어있는 경우
        if (commentLikeRepository.existsByIdUserIdAndIdCommentId(userId, commentId)) {
            throw new DuplicatedException(CustomResponseCode.DUPLICATED_LIKE);
        }

        Comment comment = commentRepository.findById(commentId).get();

        // 자신의 댓글에 좋아요한 경우
        if (userId.equals(comment.getUser().getId())) {
            throw new LikeMyselfException(CustomResponseCode.DO_NOT_LIKE_MY_COMMENT);
        }

        User user = userRepository.findById(userId).orElse(null);
        CommentLikeId commentLikeId = new CommentLikeId(userId, commentId);
        commentLikeRepository.save(new CommentLike(commentLikeId, user, comment));
    }

    // 댓글 좋아요 취소 (삭제)
    public void undoLike(Long postId, Long commentId, Long userId) {
        // postId, commentId, userId가 유효한지 확인
        checkInputs(postId, commentId, userId);

        // 사용자 요청 좋아요[userId-commentId]가 유효한지 검색
        CommentLike commentLike = commentLikeRepository.findByIdUserIdAndIdCommentId(userId, commentId).orElseThrow(
                () -> new NotFoundException(CustomResponseCode.INVALID_INPUT_VALUE)
        );

        commentLikeRepository.delete(commentLike);
    }

    // 등록되어있는 postId, commentId, userId 인지 확인하는 메서드
    private void checkInputs(Long postId, Long commentId, Long userId) {
        if (postRepository.existsById(postId)) {
            throw new NotFoundException(CustomResponseCode.POST_NOT_FOUND);
        }

        if (commentRepository.existsById(commentId)) {
            throw new NotFoundException(CustomResponseCode.COMMENT_NOT_FOUND);
        }

        if (userRepository.existsById(userId)) {
            throw new NotFoundException(CustomResponseCode.USER_NOT_FOUND);
        }
    }
}