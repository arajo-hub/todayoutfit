package com.ara.todayoutfit.member.controller;

import com.ara.todayoutfit.board.model.Declare;
import com.ara.todayoutfit.board.model.Post;
import com.ara.todayoutfit.board.service.PostService;
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
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    private PostService postService;

    @RequestMapping("/")
    public String jspCheck() {
        return "member/index";
    }

    @RequestMapping(value = "/board/list", method = {RequestMethod.GET})
    public String list(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model, String location) {
        model.addAttribute("page", 1);
        model.addAttribute("size", 10);
        model.addAttribute("location", location);
        return "member/list";
    }

    @ResponseBody
    @RequestMapping(value = "/board/listAjax", method={RequestMethod.POST})
    public PageResult listAjax(HttpServletRequest request, Model model, SearchParam searchParam) {
        searchParam.setPage((searchParam.getPage() <= 0) ? 1 : searchParam.getPage());
        PageResult result = postService.listByLocation(searchParam);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/board/addAjax", method={RequestMethod.POST})
    public BaseResult addPostAjax(HttpServletRequest request, Post post) {
        log.info(post.toString());
        post.setWriteDate(LocalDateTime.now());
        post.setDeclared_yn(Declare.NOT_DECLARED.getCode());
        return postService.add(post);
    }

    @ResponseBody
    @RequestMapping(value = "/board/recommendAjax", method = RequestMethod.POST)
    public BaseResult recommendAjax(HttpServletResponse resp, String id) throws IOException {
        return postService.recommend(Integer.parseInt(id));

    }

    @ResponseBody
    @RequestMapping(value = "/board/declareAjax", method = RequestMethod.POST)
    public BaseResult declareAjax(HttpServletResponse resp, String id) throws IOException {
        return postService.declare(Integer.parseInt(id));
    }

}