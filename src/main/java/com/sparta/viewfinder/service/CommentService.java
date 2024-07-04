package com.sparta.viewfinder.service;

import com.sparta.viewfinder.dto.CommentRequestDto;
import com.sparta.viewfinder.dto.CommentResponseDto;
import com.sparta.viewfinder.entity.Comment;
import com.sparta.viewfinder.entity.Post;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.entity.UserRoleEnum;
import com.sparta.viewfinder.exception.CommentErrorCode;
import com.sparta.viewfinder.exception.MismatchException;
import com.sparta.viewfinder.exception.NotFoundException;
import com.sparta.viewfinder.exception.PostErrorCode;
import com.sparta.viewfinder.exception.UserErrorCode;
import com.sparta.viewfinder.repository.CommentRepository;
import com.sparta.viewfinder.repository.PostRepository;
import com.sparta.viewfinder.repository.UserRepository;
import com.sparta.viewfinder.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 댓글 생성, 등록
    public CommentResponseDto createComment(
            UserDetailsImpl userDetails, Long postId, CommentRequestDto commentRequestDto) {
        // 사용자를 찾을 수 없을 때
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new NotFoundException(UserErrorCode.USER_NOT_FOUND));

        // 게시물을 찾을 수 없을 때 -> Post에 관한 ErrorCode 클래스 만들고 사용
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException(PostErrorCode.POST_NOT_FOUND));

        Comment comment = new Comment(user, post, commentRequestDto.getContent());
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    // 특정 게시물의 댓글 조회
    public List<CommentResponseDto> readComment(Long postId) {
        // 게시물을 찾을 수 없을 때 -> Post에 관한 ErrorCode 클래스 만들고 사용
        if (!postRepository.existsById(postId)) {
            throw new NotFoundException(PostErrorCode.POST_NOT_FOUND);
        }

        List<Comment> commentList = commentRepository.findAllByPostId(postId);
        List<CommentResponseDto> responseDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            Long userId = comment.getUser().getId();
            responseDtoList.add(new CommentResponseDto(comment));
        }
        return responseDtoList;
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(
        UserDetailsImpl userDetails, Long commentId, CommentRequestDto requestDto) {
        Comment comment = findComment(commentId, userDetails.getUser());
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = findComment(commentId, userDetails.getUser());
        commentRepository.delete(comment);
    }

    //본인 댓글 확인
    private Comment findComment(Long commentId, User user){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
            () -> new NotFoundException(CommentErrorCode.COMMENT_NOT_FOUND));

        validateUser(comment, user);
        return comment;
    }

    //본인 확인 및 어드민 체크
    private void validateUser(Comment comment, User user){
        boolean invalidUser = !Objects.equals(comment.getUser().getId(), user.getId());
        boolean invalidAdmin = !UserRoleEnum.ADMIN.equals(comment.getUser().getUserRole());

        if (invalidAdmin && invalidUser) {
            throw new MismatchException(UserErrorCode.USER_NOT_MATCH);
        }
    }
}