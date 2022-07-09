package com.ara.todayoutfit.admin.service.impl;

import com.ara.todayoutfit.admin.model.Admin;
import com.ara.todayoutfit.admin.repository.AdminRepository;
import com.ara.todayoutfit.admin.service.AdminService;
import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public BaseResult login(HttpSession session, Admin admin) {
        BaseResult result = new BaseResult(ResponseCode.SUCCESS);
        Optional<Admin> attemptedToLogin = adminRepository.findById(String.valueOf(admin.getId()));

        if (attemptedToLogin.isPresent()) {
            Admin loggedInAdmin = attemptedToLogin.get();
            if (admin.getPw().equals(loggedInAdmin.getPw())) {
                session.setAttribute("id", loggedInAdmin.getId());
                log.info("[{}] Logged in = {}",
                        Thread.currentThread().getStackTrace()[1].getMethodName(),
                        loggedInAdmin.getId(), loggedInAdmin.getName(), loggedInAdmin.getPw());
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
