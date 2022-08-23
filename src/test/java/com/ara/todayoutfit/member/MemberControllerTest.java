package com.ara.todayoutfit.member;

import com.ara.todayoutfit.board.model.Post;
import com.ara.todayoutfit.board.repository.PostRepository;
import com.ara.todayoutfit.member.controller.UserController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class MemberControllerTest {

    @Autowired
    private UserController memberController;

    @Autowired
    private PostRepository postRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
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
    @DisplayName("게시물 추천수 올리기")
    void recommendUpTest() throws Exception {
        int init = 0;
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
                .param("id", Long.toString(post.getPostSeq())));

        Optional<Post> postBySeq = postRepository.findBySeq(post.getPostSeq());
        Post saved = postBySeq.isPresent() ? postBySeq.get() : null;

        assertEquals(init + 1, saved.getRecommendCnt().longValue());
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
                .param("id", Long.toString(post.getPostSeq())));

        Optional<Post> postBySeq = postRepository.findBySeq(post.getPostSeq());
        Post saved = postBySeq.isPresent() ? postBySeq.get() : null;

        assertEquals(true, saved.isDeclaredYn());
    }

}
