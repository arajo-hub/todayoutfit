package com.ara.todayoutfit.user.service;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.ResultCode;
import com.ara.todayoutfit.user.domain.User;
import com.ara.todayoutfit.user.request.UserSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserService userService;

    public BaseResult login(HttpSession session, UserSearch admin) {
        BaseResult result = new BaseResult(ResultCode.SUCCESS);
        Optional<User> userFindById = userService.findById(admin.getId());

        if (userFindById.isPresent()) {
            User loggedIn = userFindById.get();
            if (admin.getPw().equals(loggedIn.getPw())) {
                session.setAttribute("id", loggedIn.getId());
                log.info("[{}] Logged in = {}",
                        Thread.currentThread().getStackTrace()[1].getMethodName(),
                        loggedIn.getId(), loggedIn.getPw());
            } else {
                result.setResultCode(ResultCode.INVALID_PARAMETER);
                log.info("[{}] {}",
                        Thread.currentThread().getStackTrace()[1].getMethodName(), result.getResultCode().getMessage());
            }
        } else {
            result.setResultCode(ResultCode.DB_NOT_FOUND_DATA);
            log.info("[{}] {}",
                    Thread.currentThread().getStackTrace()[1].getMethodName(), result.getResultCode().getMessage());
        }

        return result;

    }

}
