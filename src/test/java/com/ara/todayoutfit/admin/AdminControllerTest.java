package com.ara.todayoutfit.admin;

import com.ara.todayoutfit.admin.controller.AdminController;
import com.ara.todayoutfit.admin.model.Admin;
import com.ara.todayoutfit.admin.repository.AdminRepository;
import com.ara.todayoutfit.board.model.Declare;
import com.ara.todayoutfit.board.model.Post;
import com.ara.todayoutfit.board.repository.PostRepository;
import com.ara.todayoutfit.common.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
public class AdminControllerTest {

    @Autowired
    private AdminController adminController;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PostRepository postRepository;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    @DisplayName("관리자 로그인 성공_정상")
    void adminLoginSuccessTest() throws Exception {
        Admin successAdmin = new Admin("notExists", "admin", "1234", "joara9566@naver.com");
        if (!adminRepository.findById(successAdmin.getId()).isPresent()) {
            adminRepository.save(successAdmin);
        }
        // 로그인 시도
        mockMvc.perform(post("/admin/loginAjax")
                .flashAttr("admin", successAdmin))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").value(ResponseCode.SUCCESS.toString()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("DB에 존재하지 않는 관리자로 로그인_예외")
    void adminLoginTest() throws Exception {
        Admin adminNotExistsInDB = new Admin("notExists", "admin", "1234", "joara9566@naver.com");

        // 혹시 있다면 삭제하고 시작
        adminRepository.delete(adminNotExistsInDB);

        // 로그인 시도
        mockMvc.perform(post("/admin/loginAjax")
                .flashAttr("admin", adminNotExistsInDB))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").value(ResponseCode.DB_NOT_FOUND_DATA.toString()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("관리자 비밀번호 잘못 입력_예외")
    void adminLoginWrongPwTest() throws Exception {
        Admin admin = new Admin("wrongPassword", "admin", "1234", "joara9566@naver.com");
        Admin savedAdmin = adminRepository.save(admin);
        savedAdmin.setPw(String.format("1{}", savedAdmin.getPw()));

        // 로그인 시도
        mockMvc.perform(post("/admin/loginAjax")
                .flashAttr("admin", savedAdmin))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").value(ResponseCode.WRONG_PASSWORD.toString()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시물 삭제_정상")
    void adminDelPostTest() throws Exception {
        Post post = new Post("삭제테스트입니다", "광진구", LocalDateTime.now());
        Post savedPost = postRepository.save(post);
        log.info("{}", savedPost.getPostSeq());
        // 삭제 시도
        mockMvc.perform(post("/admin/board/deletePostAjax")
                .param("id", Integer.toString(savedPost.getPostSeq())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").value(ResponseCode.SUCCESS.toString()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시물 신고 취소_정상")
    void adminCancelDeclareTest() throws Exception {
        // 신고 취소할 게시물 생성
        Post post = new Post("신고취소테스트입니다", "광진구", LocalDateTime.now());
        postRepository.save(post);

        List<Post> posts = postRepository.findAll();
        Post recentlyInsertedPost = posts.get(posts.size() - 1);
        int recentlyInsertedPostSeq = recentlyInsertedPost.getPostSeq();
        String declaredYn = recentlyInsertedPost.getDeclared_yn();

        mockMvc.perform(post("/admin/board/cancelDeclareAjax")
                .param("id", Integer.toString(recentlyInsertedPostSeq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").value(ResponseCode.SUCCESS.toString()))
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertThat(Declare.NOT_DECLARED.getCode()).isEqualTo(declaredYn);
    }

}
