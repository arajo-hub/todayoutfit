package com.ara.todayoutfit.post.service;

import com.ara.todayoutfit.common.ResultCode;
import com.ara.todayoutfit.config.exception.InvalidParameterException;
import com.ara.todayoutfit.config.exception.NotFoundException;
import com.ara.todayoutfit.post.domain.Post;
import com.ara.todayoutfit.post.repository.PostRepository;
import com.ara.todayoutfit.post.request.PostCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 저장 성공")
    void post_save_success() {
        PostCreateRequest request = PostCreateRequest.builder()
                .content("저장테스트")
                .location("광진구")
                .build();
        postService.savePost(request);

        Post postBySeq = postRepository.findBySeq(1L).orElseThrow(() -> new NotFoundException());

        assertEquals(request.getContent(), postBySeq.getContent());
        assertEquals(request.getLocation(), postBySeq.getLocation());
    }

    @Test
    @DisplayName("게시글 삭제 성공")
    void post_delete_success() {
        PostCreateRequest request = PostCreateRequest.builder()
                .content("삭제테스트")
                .location("광진구")
                .build();
        postService.savePost(request);

        postService.deletePost(1L);

        assertThatThrownBy(() -> postRepository.findBySeq(1L).get())
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("게시글 신고 성공")
    void post_declare_success() {
        PostCreateRequest request = PostCreateRequest.builder()
                .content("신고테스트")
                .location("광진구")
                .build();
        postService.savePost(request);

        postService.declarePost(1L);

        Post postBySeq = postRepository.findBySeq(1L).orElseThrow(() -> new NotFoundException());

        assertTrue(postBySeq.isDeclaredYn());
    }

    @Test
    @DisplayName("게시글 신고 취소 성공")
    void post_cancel_declare_success() {
        PostCreateRequest request = PostCreateRequest.builder()
                .content("신고취소테스트")
                .location("광진구")
                .build();
        postService.savePost(request);

        postService.declarePost(1L);
        postService.cancelDeclare(1L);

        Post postBySeq = postRepository.findBySeq(1L).orElseThrow(() -> new NotFoundException());

        assertFalse(postBySeq.isDeclaredYn());
    }

    @Test
    @DisplayName("게시글 좋아요 성공")
    void post_like_success() {
        PostCreateRequest request = PostCreateRequest.builder()
                .content("좋아요테스트")
                .location("광진구")
                .build();
        postService.savePost(request);

        postService.likePost(1L, "127.0.0.1");

        Post postBySeq = postRepository.findBySeq(1L).orElseThrow(() -> new NotFoundException());
        long likeCount = postBySeq.getLikeCount();

        assertEquals(1, likeCount);
    }

    @Test
    @DisplayName("게시글 좋아요 취소 성공")
    void post_cancel_like_success() {
        PostCreateRequest request = PostCreateRequest.builder()
                .content("좋아요취소테스트")
                .location("광진구")
                .build();
        postService.savePost(request);

        postService.likePost(1L, "127.0.0.1");
        postService.likePost(1L, "127.0.0.1");

        Post postBySeq = postRepository.findBySeq(1L).orElseThrow(() -> new NotFoundException());
        long likeCount = postBySeq.getLikeCount();

        assertEquals(0, likeCount);
    }
}