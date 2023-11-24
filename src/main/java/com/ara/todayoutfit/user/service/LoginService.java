package com.ara.todayoutfit.user.service;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Service
public class LoginService {

    @Autowired
    private UserService userService;

    public BaseResult login(HttpSession session, com.ara.todayoutfit.user.service.request.UserSearch admin) {
        BaseResult result = new BaseResult(ResponseCode.SUCCESS);
        Optional<com.ara.todayoutfit.user.service.domain.User> userFindById = userService.findById(admin.getId());

        if (userFindById.isPresent()) {
            com.ara.todayoutfit.user.service.domain.User loggedIn = userFindById.get();
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
