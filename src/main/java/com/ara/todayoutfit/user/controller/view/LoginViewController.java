package com.ara.todayoutfit.user.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController {

    @GetMapping("/admin/login")
    public String adminLogin() {
        return "admin/login";
    }

}
