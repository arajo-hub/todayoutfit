package com.ara.todayoutfit.member.controller;

import com.ara.todayoutfit.post.domain.Post;
import com.ara.todayoutfit.post.domain.PostLike;
import com.ara.todayoutfit.post.repository.PostLikeRepository;
import com.ara.todayoutfit.post.repository.PostRepository;
import com.ara.todayoutfit.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class MemberControllerTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
        postLikeRepository.deleteAll();
    }

    @Test
    @DisplayName("게시물 추가")
    void addPostTest() throws Exception {
        Post post = Post.builder()
                .content("게시물 추가 테스트입니다.")
                .location("광진구")
                .declaredYn(false)
                .recommendCnt(0)
                .writeDate(LocalDateTime.now())
                .build();

        // 게시물 추가
        mockMvc.perform(post("/board/addAjax")
                        .flashAttr("post", post));

        List<Post> all = postRepository.findAll();
        assertEquals(1, all.size());
    }

    @Test
    @DisplayName("50자 이상 게시물 추가")
    void addLongLengthPostTest() throws Exception {
        Post post = Post.builder()
                .content("게시물추가게시물추가게시물추가게시물추가게시물추가게시물추가게시물추가게시물추가게시물추가게시물추가게시물추가")
                .location("광진구")
                .declaredYn(false)
                .recommendCnt(0)
                .writeDate(LocalDateTime.now())
                .build();

        // 게시물 추가
        mockMvc.perform(post("/board/addAjax")
                .flashAttr("post", post))
                .andExpect(jsonPath("$.responseCode").value(ResultCode.INVALID_PARAMETER.toString()));

    }

    @Test
    @DisplayName("내용없는 게시물 추가")
    void addNoLengthPostTest() throws Exception {
        Post post = Post.builder()
                .content("")
                .location("광진구")
                .declaredYn(false)
                .recommendCnt(0)
                .writeDate(LocalDateTime.now())
                .build();

        // 게시물 추가
        mockMvc.perform(post("/board/addAjax")
                .flashAttr("post", post))
                .andExpect(jsonPath("$.responseCode").value(ResultCode.INVALID_PARAMETER.toString()));

    }

    @Test
    @DisplayName("15자 이상 지역으로 게시글 추가")
    void addLongLocationTest() throws Exception {
        Post post = Post.builder()
                .content("저장테스트")
                .location("광진구광진구광진구광진구광진구광진구")
                .declaredYn(false)
                .recommendCnt(0)
                .writeDate(LocalDateTime.now())
                .build();

        // 게시물 추가
        mockMvc.perform(post("/board/addAjax")
                .flashAttr("post", post))
                .andExpect(jsonPath("$.responseCode").value(ResultCode.INVALID_PARAMETER.toString()));

    }

    @Test
    @DisplayName("빈 지역명으로 게시글 추가")
    void addNoLengthLocationTest() throws Exception {
        Post post = Post.builder()
                .content("저장테스트")
                .location("")
                .declaredYn(false)
                .recommendCnt(0)
                .writeDate(LocalDateTime.now())
                .build();

        // 게시물 추가
        mockMvc.perform(post("/board/addAjax")
                .flashAttr("post", post))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").value(ResultCode.INVALID_PARAMETER.toString()))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("좋아요 버튼 클릭")
    void recommend() throws Exception {
        long init = 0;
        Post post = Post.builder()
                .content("게시물 추천 테스트입니다.")
                .location("광진구")
                .declaredYn(false)
                .recommendCnt(init)
                .writeDate(LocalDateTime.now())
                .build();
        postRepository.save(post);

        // 추천수 올리기
        mockMvc.perform(post("/board/recommendAjax")
                .param("id", Long.toString(post.getPostId())));

        List<PostLike> posts = postLikeRepository.findAll();
        Optional<Post> postBySeq = postRepository.findBySeq(post.getPostId());
        Post saved = postBySeq.isPresent() ? postBySeq.get() : null;

        assertFalse(posts.isEmpty());
        assertEquals(init + 1, saved.getRecommendCnt().longValue());
    }

    @Test
    @DisplayName("좋아요 취소")
    void cancelRecommend() throws Exception {
        long init = 0;
        Post post = Post.builder()
                .content("게시물 추천 테스트입니다.")
                .location("광진구")
                .declaredYn(false)
                .recommendCnt(init)
                .writeDate(LocalDateTime.now())
                .build();
        postRepository.save(post);

        // 추천수 올리기
        mockMvc.perform(post("/board/recommendAjax")
                .param("id", Long.toString(post.getPostId())));

        // 추천수 올리기
        mockMvc.perform(post("/board/recommendAjax")
                .param("id", Long.toString(post.getPostId())));

        List<PostLike> postLikes = postLikeRepository.findAll();
        Optional<Post> postBySeq = postRepository.findBySeq(post.getPostId());
        Post saved = postBySeq.isPresent() ? postBySeq.get() : null;

        assertTrue(postLikes.isEmpty());
        assertEquals(init, saved.getRecommendCnt().longValue());
    }

    @Test
    @DisplayName("게시물 신고")
    void declareTest() throws Exception {
        Post post = Post.builder()
                .content("게시물 추천 테스트입니다.")
                .location("광진구")
                .declaredYn(false)
                .recommendCnt(0)
                .writeDate(LocalDateTime.now())
                .build();
        postRepository.save(post);

        // 신고
        mockMvc.perform(post("/board/declareAjax")
                .param("id", Long.toString(post.getPostId())));

        Optional<Post> postBySeq = postRepository.findBySeq(post.getPostId());
        Post saved = postBySeq.isPresent() ? postBySeq.get() : null;

        assertEquals(true, saved.isDeclaredYn());
    }

}
