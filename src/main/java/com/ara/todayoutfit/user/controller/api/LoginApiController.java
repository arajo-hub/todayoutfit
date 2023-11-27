package com.ara.todayoutfit.user.controller.api;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.ResultCode;
import com.ara.todayoutfit.user.request.UserSearch;
import com.ara.todayoutfit.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class LoginApiController {

    private final LoginService loginService;

    @PostMapping("/login")
    public BaseResult login(HttpSession session, @RequestBody UserSearch admin) {
        return loginService.login(session, admin);
    }

}
