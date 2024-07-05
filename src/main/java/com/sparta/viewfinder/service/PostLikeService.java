package com.sparta.viewfinder.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.viewfinder.dto.common.CustomResponseCode;
import com.sparta.viewfinder.dto.post.PostResponseDto;
import com.sparta.viewfinder.entity.Post;
import com.sparta.viewfinder.entity.PostLike;
import com.sparta.viewfinder.entity.PostLikeId;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.exception.DuplicatedException;
import com.sparta.viewfinder.exception.LikeMyselfException;
import com.sparta.viewfinder.exception.NotFoundException;
import com.sparta.viewfinder.repository.PostLikeRepository;
import com.sparta.viewfinder.repository.PostRepository;
import com.sparta.viewfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JPAQueryFactory queryFactory;

    // 게시글 좋아요 등록
    public void doLike(Long postId, Long userId) {
        // postId, userId가 유효한지 확인 (등록되어있는 postId, userId인지 확인)
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException(CustomResponseCode.POST_NOT_FOUND)
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(CustomResponseCode.USER_NOT_FOUND)
        );

        // 이미 해당 userId-postId 로 좋아요 등록되어있는 경우
        if (postLikeRepository.existsByIdUserIdAndIdPostId(userId, postId)) {
            throw new DuplicatedException(CustomResponseCode.DUPLICATED_LIKE);
        }

        // 자신의 게시글에 좋아요한 경우
        if (userId.equals(post.getUser().getId())) {
            throw new LikeMyselfException(CustomResponseCode.DO_NOT_LIKE_MY_COMMENT);
        }

        postLikeRepository.save(new PostLike(new PostLikeId(userId, postId) ,user, post));
    }

    // 게시글 좋아요 취소 (삭제)
    public void undoLike(Long postId, Long userId) {
        // postId, userId가 유효한지 확인 (등록되어있는 postId, userId인지 확인)
        if (!postRepository.existsById(postId)) {
            throw new NotFoundException(CustomResponseCode.POST_NOT_FOUND);
        }
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(CustomResponseCode.USER_NOT_FOUND);
        }

        // 사용자 요청 좋아요[userId-postId]가 유효한지 검색
        PostLike postLike = postLikeRepository.findByIdUserIdAndIdPostId(userId, postId).orElseThrow(
                () -> new NotFoundException(CustomResponseCode.INVALID_INPUT_VALUE)
        );

        postLikeRepository.delete(postLike);
    }

    public Page<PostResponseDto> getPosts(int page, Long userId) {
        // user 가 유효한지 확인
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(CustomResponseCode.USER_NOT_FOUND);
        }

        // 페이징
        int PAGE_SIZE = 5;
        String sortBy = "createdAt";
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);

        QPost post = QPost.post;
        QPostLike postLike = QPostLike.postLike;

        List<Post> posts = queryFactory
                .selectFrom(post)
                .innerJoin(postLike).on(post.id.eq(postLike.post.id))
                .where(postLike.user.id.eq(userId))
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        List<PostResponseDto> postResponseDtos = posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        long total = queryFactory.selectFrom(post)
                .innerJoin(postLike).on(post.id.eq(postLike.post.id))
                .where(postLike.user.id.eq(userId))
                .fetchCount();

        return new PageImpl<>(postResponseDtos, pageable, total);
    }
}