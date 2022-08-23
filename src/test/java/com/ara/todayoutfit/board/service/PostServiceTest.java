package com.ara.todayoutfit.board.service;

import com.ara.todayoutfit.board.model.Post;
import com.ara.todayoutfit.board.model.PostSearch;
import com.ara.todayoutfit.board.repository.PostRepository;
import com.ara.todayoutfit.common.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @DisplayName("% 검색")
    void findByPercentageSymbol() {
        List<Post> posts = List.of(Post.builder()
                        .content("옷차림 글 테스트1")
                        .location("광진구")
                        .recommendCnt(1L)
                        .declaredYn(false)
                        .writeDate(LocalDateTime.now())
                        .build(),
                Post.builder()
                        .content("옷차림 글 테스트2")
                        .location("광진구")
                        .recommendCnt(1L)
                        .declaredYn(false)
                        .writeDate(LocalDateTime.now())
                        .build());
        postService.saveAll(posts);

        PostSearch searchParam = PostSearch.builder()
                .page(1)
                .size(10)
                .location("%")
                .build();

        PageResult pageResult = postService.findByLocation(searchParam);

        assertNotEquals(posts.size(), pageResult.getList().getNumberOfElements());
    }

    @Test
    @DisplayName("글 목록 조회")
    void findAll() {
        List<Post> posts = List.of(Post.builder()
                        .content("옷차림 글 테스트1")
                        .location("광진구")
                        .recommendCnt(1L)
                        .declaredYn(false)
                        .writeDate(LocalDateTime.now())
                        .build(),
                Post.builder()
                        .content("옷차림 글 테스트2")
                        .location("광진구")
                        .recommendCnt(1L)
                        .declaredYn(false)
                        .writeDate(LocalDateTime.now())
                        .build());
        postService.saveAll(posts);

        PostSearch searchParam = PostSearch.builder()
                        .page(1)
                        .size(10)
                        .location(null)
                        .build();

        PageResult pageResult = postService.findAll(searchParam);

        assertEquals(posts.size(), pageResult.getList().getNumberOfElements());
    }

    @Test
    @DisplayName("위치명으로 글 검색")
    void findByLocation() {
        List<Post> posts = List.of(Post.builder()
                        .content("옷차림 글 테스트1")
                        .location("광진구")
                        .recommendCnt(1L)
                        .declaredYn(false)
                        .writeDate(LocalDateTime.now())
                        .build(),
                Post.builder()
                        .content("옷차림 글 테스트2")
                        .location("강남구")
                        .recommendCnt(1L)
                        .declaredYn(false)
                        .writeDate(LocalDateTime.now())
                        .build());
        postService.saveAll(posts);

        String location = "광진구";
        PostSearch searchParam = PostSearch.builder()
                .page(1)
                .size(10)
                .location(location)
                .build();

        PageResult pageResult = postService.findByLocation(searchParam);

        List<Post> filtered = posts.stream().filter(p -> p.getLocation().equals(location)).collect(Collectors.toList());

        assertEquals(filtered.size(), pageResult.getList().getNumberOfElements());
    }

    @Test
    @DisplayName("글 삭제")
    void delete() {
        Post post = Post.builder()
                .content("삭제테스트")
                .location("광진구")
                .recommendCnt(1L)
                .declaredYn(false)
                .writeDate(LocalDateTime.now())
                .build();
        postService.save(post);
        postRepository.deleteBySeq(post.getPostSeq());
        List<Post> all = postRepository.findAll();

        assertEquals(0, all.size());
    }

    @Test
    @DisplayName("글 신고 취소")
    void cancelDeclare() {
        Post post = Post.builder()
                .content("삭제테스트")
                .location("광진구")
                .recommendCnt(1L)
                .declaredYn(true)
                .writeDate(LocalDateTime.now())
                .build();
        postService.save(post);

        postService.cancelDeclare(post.getPostSeq());

        Optional<Post> postFindBySeq = postRepository.findBySeq(post.getPostSeq());
        Post result = new Post();
        if (postFindBySeq.isPresent()) {
            result = postFindBySeq.get();
        }

        assertEquals(false, result.isDeclaredYn());
    }

    @Test
    @DisplayName("글 저장")
    void save() {
        Post post = Post.builder()
                .content("삭제테스트")
                .location("광진구")
                .recommendCnt(1L)
                .declaredYn(false)
                .writeDate(LocalDateTime.now())
                .build();
        postService.save(post);

        Optional<Post> postBySeq = postRepository.findBySeq(post.getPostSeq());

        assertEquals(1, postBySeq.stream().count());
    }

    @Test
    @DisplayName("글 추천")
    void recommend() {
        long init = 0;

        Post post = Post.builder()
                .content("삭제테스트")
                .location("광진구")
                .recommendCnt(init)
                .declaredYn(false)
                .writeDate(LocalDateTime.now())
                .build();
        postService.save(post);

        postService.recommend(post.getPostSeq());

        Optional<Post> postBySeq = postRepository.findBySeq(post.getPostSeq());
        Post result = new Post();
        if (postBySeq.isPresent()) {
            result = postBySeq.get();
        }

        assertEquals(init + 1, result.getRecommendCnt().longValue());
    }

    @Test
    @DisplayName("글 신고")
    void declare() {
        Post post = Post.builder()
                .content("삭제테스트")
                .location("광진구")
                .recommendCnt(0)
                .declaredYn(false)
                .writeDate(LocalDateTime.now())
                .build();
        postService.save(post);

        postService.declare(post.getPostSeq());

        Optional<Post> postBySeq = postRepository.findBySeq(post.getPostSeq());
        Post result = new Post();
        if (postBySeq.isPresent()) {
            result = postBySeq.get();
        }
        assertEquals(true, result.isDeclaredYn());

    }
}