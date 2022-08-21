package com.ara.todayoutfit.admin.service;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.ResponseCode;
import com.ara.todayoutfit.user.model.Member;
import com.ara.todayoutfit.user.model.MemberSearch;
import com.ara.todayoutfit.user.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("로그인 성공")
    void login() {
        Member admin = Member.builder()
                .id("admin")
                .pw("1234")
                .build();
        memberService.save(admin);

        MemberSearch userSearch = MemberSearch.builder()
                .id(admin.getId())
                .pw(admin.getPw())
                .build();

        BaseResult result = adminService.login(new MockHttpSession(null, admin.getId()), userSearch);

        assertEquals(ResponseCode.SUCCESS, result.getResponseCode());
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 로그인 시도")
    void loginNotExistId() {
        Member admin = Member.builder()
                .id("admin")
                .pw("1234")
                .build();
        memberService.delete(admin);

        MemberSearch userSearch = MemberSearch.builder()
                .id(admin.getId())
                .pw(admin.getPw())
                .build();

        BaseResult result = adminService.login(new MockHttpSession(null, admin.getId()), userSearch);

        assertEquals(ResponseCode.DB_NOT_FOUND_DATA, result.getResponseCode());
    }

    @Test
    @DisplayName("일치하지 않는 비밀번호로 로그인 시도")
    void loginWrongPw() {
        Member admin = Member.builder()
                .id("admin")
                .pw("1234")
                .build();
        memberService.save(admin);

        MemberSearch userSearch = MemberSearch.builder()
                .id(admin.getId())
                .pw("564654")
                .build();

        BaseResult result = adminService.login(new MockHttpSession(null, admin.getId()), userSearch);

        assertEquals(ResponseCode.WRONG_PASSWORD, result.getResponseCode());
    }

}