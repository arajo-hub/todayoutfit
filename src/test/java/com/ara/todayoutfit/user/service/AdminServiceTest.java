package com.ara.todayoutfit.user.service;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.PwdEncryption;
import com.ara.todayoutfit.common.ResponseCode;
import com.ara.todayoutfit.user.domain.User;
import com.ara.todayoutfit.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class AdminServiceTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository memberRepository;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void login() {
        User admin = User.builder()
                .id("admin")
                .pw(PwdEncryption.encrypt("1234"))
                .build();
        userService.save(admin);

        com.ara.todayoutfit.user.service.request.UserSearch userSearch = com.ara.todayoutfit.user.service.request.UserSearch.builder()
                .id(admin.getId())
                .pw(admin.getPw())
                .build();

        BaseResult result = loginService.login(new MockHttpSession(null, admin.getId()), userSearch);

        assertEquals(ResponseCode.SUCCESS, result.getResponseCode());
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 로그인 시도")
    void loginNotExistId() {
        com.ara.todayoutfit.user.service.request.UserSearch userSearch = com.ara.todayoutfit.user.service.request.UserSearch.builder()
                .id("admin")
                .pw(PwdEncryption.encrypt("1234"))
                .build();

        BaseResult result = loginService.login(new MockHttpSession(null, userSearch.getId()), userSearch);

        assertEquals(ResponseCode.DB_NOT_FOUND_DATA, result.getResponseCode());
    }

    @Test
    @DisplayName("일치하지 않는 비밀번호로 로그인 시도")
    void loginWrongPw() {
        User admin = User.builder()
                .id("admin")
                .pw(PwdEncryption.encrypt("1234"))
                .build();
        userService.save(admin);

        com.ara.todayoutfit.user.service.request.UserSearch userSearch = com.ara.todayoutfit.user.service.request.UserSearch.builder()
                .id(admin.getId())
                .pw(PwdEncryption.encrypt("564654"))
                .build();

        BaseResult result = loginService.login(new MockHttpSession(null, admin.getId()), userSearch);

        assertEquals(ResponseCode.WRONG_PASSWORD, result.getResponseCode());
    }

}