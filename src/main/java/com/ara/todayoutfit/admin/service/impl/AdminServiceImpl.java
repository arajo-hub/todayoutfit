package com.ara.todayoutfit.admin.service.impl;

import com.ara.todayoutfit.admin.service.AdminService;
import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.ResponseCode;
import com.ara.todayoutfit.member.model.Member;
import com.ara.todayoutfit.member.model.MemberSearch;
import com.ara.todayoutfit.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private MemberService userService;

    @Override
    public BaseResult login(HttpSession session, MemberSearch admin) {
        BaseResult result = new BaseResult(ResponseCode.SUCCESS);
        Optional<Member> userFindById = userService.findById(admin.getId());

        if (userFindById.isPresent()) {
            Member loggedIn = userFindById.get();
            if (admin.getPw().equals(loggedIn.getPw())) {
                session.setAttribute("id", loggedIn.getId());
                log.info("[{}] Logged in = {}",
                        Thread.currentThread().getStackTrace()[1].getMethodName(),
                        loggedIn.getId(), loggedIn.getPw());
            } else {
                result.setResponseCode(ResponseCode.WRONG_PASSWORD);
                log.info("[{}] {}",
                        Thread.currentThread().getStackTrace()[1].getMethodName(), result.getResponseCode().getMessage());
            }
        } else {
            result.setResponseCode(ResponseCode.DB_NOT_FOUND_DATA);
            log.info("[{}] {}",
                    Thread.currentThread().getStackTrace()[1].getMethodName(), result.getResponseCode().getMessage());
        }

        return result;

    }

}
