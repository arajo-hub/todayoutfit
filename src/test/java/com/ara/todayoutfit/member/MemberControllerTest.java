package com.ara.todayoutfit.member;

import com.ara.todayoutfit.board.model.Declare;
import com.ara.todayoutfit.board.model.Post;
import com.ara.todayoutfit.board.repository.PostRepository;
import com.ara.todayoutfit.member.controller.MemberController;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
@SpringBootTest
public class MemberControllerTest {

    @Autowired
    private MemberController memberController;
    @Autowired
    private PostRepository postRepository;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    @DisplayName("게시물 추가")
    void addPostTest() throws Exception {
        Post post = new Post("게시물추가테스트입니다","광진구", LocalDateTime.now());

        // 게시물 추가
        mockMvc.perform(post("/board/addAjax")
                        .flashAttr("post", post));

        List<Post> all = postRepository.findAll();
        Assertions.assertThat(all.contains(post));
    }

    @Test
    @DisplayName("게시물 추천수 올리기")
    void recommendUpTest() throws Exception {
        Post post = new Post("게시물추천수올리기테스트입니다","광진구", LocalDateTime.now());
        Post savedPost = postRepository.save(post);

        // 추천수 올리기
        mockMvc.perform(post("/board/recommendAjax")
                .param("id", Integer.toString(savedPost.getPostSeq())));
        Post postByDB = postRepository.findById(savedPost.getPostSeq()).get();

        Assertions.assertThat(post.getRecommendCnt() + 1).isEqualTo(postByDB.getRecommendCnt());
    }

    @Test
    @DisplayName("게시물 신고")
    void declareTest() throws Exception {
        Post post = new Post("게시물신고테스트입니다", "광진구", LocalDateTime.now());
        Post savedPost = postRepository.save(post);

        // 신고
        mockMvc.perform(post("/board/declareAjax")
                .param("id", Integer.toString(savedPost.getPostSeq())));

        Post postByDB = postRepository.findById(savedPost.getPostSeq()).get();

        Assertions.assertThat(Declare.DECLARED.getCode()).isEqualTo(postByDB.getDeclared_yn());
    }

}
