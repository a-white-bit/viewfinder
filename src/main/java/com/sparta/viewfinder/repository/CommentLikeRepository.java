package com.sparta.viewfinder.repository;

import com.sparta.viewfinder.entity.CommentLike;
import com.sparta.viewfinder.entity.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {
    Optional<CommentLike> findByIdUserIdAndIdCommentId(Long userId, Long commentId);
    boolean existsByIdUserIdAndIdCommentId(Long userId, Long commentId);
}
