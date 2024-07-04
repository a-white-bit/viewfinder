package com.sparta.viewfinder.service;

import com.sparta.viewfinder.dto.PostRequestDto;
import com.sparta.viewfinder.dto.PostResponseDto;
import com.sparta.viewfinder.entity.Post;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.entity.UserRoleEnum;
import com.sparta.viewfinder.exception.MismatchException;
import com.sparta.viewfinder.exception.NotFoundException;
import com.sparta.viewfinder.exception.PostErrorCode;
import com.sparta.viewfinder.exception.UserErrorCode;
import com.sparta.viewfinder.repository.PostRepository;
import com.sparta.viewfinder.repository.UserRepository;
import com.sparta.viewfinder.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponseDto createPost(UserDetailsImpl userDetails, PostRequestDto requestDto) {
        User user = userRepository.findById(userDetails.getUser().getId())
            .orElseThrow(() -> new NotFoundException(UserErrorCode.USER_NOT_FOUND));
        Post post = new Post(user, requestDto.getContent());
        postRepository.save(post);

        return new PostResponseDto(post);
    }

    public PostResponseDto readPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
            () -> new NotFoundException(PostErrorCode.POST_NOT_FOUND));
        return new PostResponseDto(post);
    }

    public Page<PostResponseDto> readAllPost(int page) {
        // 페이지네이션
        int PAGE_SIZE = 5;
        String sortBy = "createdAt";
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);

        Page<Post> postList = postRepository.findAll(pageable);

        return postList.map(PostResponseDto::new);
    }

    @Transactional
    public PostResponseDto updatePost(
            Long postId, UserDetailsImpl userDetails, PostRequestDto requestDto) {
        Post post = findPost(postId, userDetails.getUser());
        post.update(requestDto);
        return new PostResponseDto(post);
    }

    @Transactional
    public void deletePost(Long postId, UserDetailsImpl userDetails) {
        Post post = findPost(postId, userDetails.getUser());
        postRepository.delete(post);
    }

    //본인 게시글 확인
    private Post findPost(Long postId, User user){
        Post post = postRepository.findById(postId).orElseThrow(
            () -> new NotFoundException(PostErrorCode.POST_NOT_FOUND));

        validateUser(post, user);
        return post;
    }

    //본인 확인 및 어드민 체크
    private void validateUser(Post post, User user){
        boolean invalidUser = !Objects.equals(post.getUser().getId(), user.getId());
        boolean invalidAdmin = !UserRoleEnum.ADMIN.equals(user.getUserRole());

        if (invalidAdmin && invalidUser) {
            throw new MismatchException(UserErrorCode.USER_NOT_MATCH);
        }
    }
}
