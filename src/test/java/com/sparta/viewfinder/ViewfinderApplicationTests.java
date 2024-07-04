package com.sparta.viewfinder;

import com.sparta.viewfinder.entity.Post;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.repository.PostRepository;
import com.sparta.viewfinder.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ViewfinderApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("User & Post 더미 테스트 추가")
    @Transactional
    @Rollback(value = false)
    void setUserAndPost() {
        User user = new User();
        userRepository.save(user);

        Post post = new Post(user, "글 내용입니다.");
        postRepository.save(post);
    }

}