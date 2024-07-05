package com.sparta.viewfinder.repository;

import com.sparta.viewfinder.entity.PostLike;
import com.sparta.viewfinder.entity.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    Optional<PostLike> findByIdUserIdAndIdPostId(Long userId, Long postId);
    boolean existsByIdUserIdAndIdPostId(Long userId, Long postId);
}
