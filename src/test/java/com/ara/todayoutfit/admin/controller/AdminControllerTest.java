package com.ara.todayoutfit.admin.controller;

import com.ara.todayoutfit.post.domain.Post;
import com.ara.todayoutfit.post.repository.PostRepository;
import com.ara.todayoutfit.common.PwdEncryption;
import com.ara.todayoutfit.common.ResponseCode;
import com.ara.todayoutfit.member.model.Member;
import com.ara.todayoutfit.member.model.MemberSearch;
import com.ara.todayoutfit.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class AdminControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("관리자 로그인 성공")
    void adminLoginSuccessTest() throws Exception {
        String pwd = "1234";
        Member successAdmin = Member.builder()
                .id("admin")
                .pw(PwdEncryption.encrypt(pwd))
                .isAdmin(true)
                .build();
        memberRepository.save(successAdmin);
        MemberSearch memberSearch = MemberSearch.builder()
                        .id(successAdmin.getId())
                        .pw(pwd)
                        .build();
        //로그인 시도
        mockMvc.perform(post("/admin/loginAjax")
                .flashAttr("memberSearch", memberSearch))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").value(ResponseCode.SUCCESS.toString()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("DB에 존재하지 않는 관리자로 로그인")
    void adminLoginTest() throws Exception {
        MemberSearch memberSearch = MemberSearch.builder()
                .id("admin")
                .pw("1234")
                .build();
        // 로그인 시도
        mockMvc.perform(post("/admin/loginAjax")
                .flashAttr("memberSearch", memberSearch))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").value(ResponseCode.DB_NOT_FOUND_DATA.toString()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("관리자 비밀번호 잘못 입력")
    void adminLoginWrongPwTest() throws Exception {
        Member admin = Member.builder()
                .id("admin")
                .pw(PwdEncryption.encrypt("1234"))
                .isAdmin(true)
                .build();
        memberRepository.save(admin);

        MemberSearch memberSearch = MemberSearch.builder()
                        .id(admin.getId())
                        .pw(String.format("1{}", admin.getPw()))
                        .build();

        // 로그인 시도
        mockMvc.perform(post("/admin/loginAjax")
                .flashAttr("memberSearch", memberSearch))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").value(ResponseCode.WRONG_PASSWORD.toString()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시물 삭제")
    void adminDelPostTest() throws Exception {
        Post post = Post.builder()
                .content("삭제테스트입니다")
                .location("광진구")
                .writeDate(LocalDateTime.now())
                .recommendCnt(0)
                .declaredYn(false)
                .build();
        Post savedPost = postRepository.save(post);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", "admin");

        // 삭제 시도
        mockMvc.perform(post("/admin/board/deletePostAjax")
                .session(session)
                .param("id", Long.toString(savedPost.getPostId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").value(ResponseCode.SUCCESS.toString()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시물 신고 취소")
    void adminCancelDeclareTest() throws Exception {
        // 신고 취소할 게시물 생성
        Post post = Post.builder()
                .content("삭제테스트입니다")
                .location("광진구")
                .writeDate(LocalDateTime.now())
                .recommendCnt(0)
                .declaredYn(true)
                .build();
        Post savedPost = postRepository.save(post);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", "admin");

        mockMvc.perform(post("/admin/board/cancelDeclareAjax")
                .session(session)
                .param("id", Long.toString(savedPost.getPostId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").value(ResponseCode.SUCCESS.toString()))
                .andDo(MockMvcResultHandlers.print());
    }

}
