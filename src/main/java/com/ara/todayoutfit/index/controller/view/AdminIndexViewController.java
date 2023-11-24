package com.ara.todayoutfit.index.controller.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminIndexViewController {

    @RequestMapping(value = "/post/list", method = {RequestMethod.GET})
    public String list(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        model.addAttribute("page", 1);
        model.addAttribute("size", 10);
        return "admin/list";
    }

}
