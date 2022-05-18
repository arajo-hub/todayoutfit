package com.ara.todayoutfit.admin;

import com.ara.todayoutfit.board.*;
import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.PageResult;
import com.ara.todayoutfit.common.SearchParam;
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
    public BaseResult loginAjax(HttpServletRequest request, HttpServletResponse response, HttpSession session, Admin admin) {
        // 유효성 체크
        log.info(admin.toString());
        return adminService.login(session, admin);
    }

    @RequestMapping(value = "/board/list", method = {RequestMethod.GET})
    public String list(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        model.addAttribute("page", 1);
        model.addAttribute("size", 10);
        return "admin/list";
    }

    @ResponseBody
    @RequestMapping(value = "/board/listAjax", method = {RequestMethod.POST})
    public PageResult listAjax(HttpServletRequest request, HttpServletResponse response, HttpSession session, SearchParam searchParam) {
        searchParam.setPage((searchParam.getPage() <= 0) ? 1 : searchParam.getPage());
        PageResult result = postService.list(searchParam);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/board/deletePostAjax", method = {RequestMethod.POST})
    public BaseResult deletePostAjax(HttpServletRequest request, HttpServletResponse response, HttpSession session, String id) {
        return postService.deletePost(Integer.parseInt(id));
    }

    @ResponseBody
    @RequestMapping(value = "/board/cancelDeclareAjax", method = {RequestMethod.POST})
    public BaseResult cancelDeclareAjax(HttpServletRequest request, HttpServletResponse response, HttpSession session, String id) {
        return postService.cancelDeclare(Integer.parseInt(id));
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET})
    public String adminLogout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        session.invalidate();
        log.info("[{}] Logout");
        return "redirect:/admin/login";
    }

}
