package com.ara.todayoutfit.admin.controller;

import com.ara.todayoutfit.admin.service.AdminService;
import com.ara.todayoutfit.board.model.PostSearch;
import com.ara.todayoutfit.board.service.PostService;
import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.PageResult;
import com.ara.todayoutfit.member.model.MemberSearch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        log.info("[{}]", Thread.currentThread().getStackTrace()[1].getMethodName());
        return "admin/login";
    }

    @ResponseBody
    @RequestMapping(value = "/loginAjax", method = {RequestMethod.POST})
    public BaseResult loginAjax(HttpServletRequest request, HttpServletResponse response, HttpSession session, MemberSearch memberSearch) {
        // 유효성 체크
        log.info(memberSearch.toString());
        return adminService.login(session, memberSearch);
    }

    @RequestMapping(value = "/board/list", method = {RequestMethod.GET})
    public String list(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        model.addAttribute("page", 1);
        model.addAttribute("size", 10);
        return "admin/list";
    }

    @ResponseBody
    @RequestMapping(value = "/board/listAjax", method = {RequestMethod.POST})
    public PageResult listAjax(HttpServletRequest request, HttpServletResponse response, HttpSession session, PostSearch postSearch) {
        postSearch.setPage((postSearch.getPage() <= 0) ? 1 : postSearch.getPage());
        PageResult result = postService.findAll(postSearch);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/board/deletePostAjax", method = {RequestMethod.POST})
    public BaseResult deletePostAjax(HttpServletRequest request, HttpServletResponse response, HttpSession session, String id) {
        return postService.delete(Long.parseLong(id));
    }

    @ResponseBody
    @RequestMapping(value = "/board/cancelDeclareAjax", method = {RequestMethod.POST})
    public BaseResult cancelDeclareAjax(HttpServletRequest request, HttpServletResponse response, HttpSession session, String id) {
        return postService.cancelDeclare(Long.parseLong(id));
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET})
    public String adminLogout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        session.invalidate();
        log.info("[{}] Logout");
        return "redirect:/admin/login";
    }

}
