package com.ara.todayoutfit.member.controller;

import com.ara.todayoutfit.board.model.Post;
import com.ara.todayoutfit.board.model.PostSearch;
import com.ara.todayoutfit.board.service.PostService;
import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Controller
public class MemberController {

    @Value("${view.apiKey}")
    private String apiKey;

    @Autowired
    private PostService postService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("apiKey", apiKey);
        return "member/index";
    }

    @RequestMapping(value = "/board/list", method = {RequestMethod.GET})
    public String list(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model, String location) {
        model.addAttribute("apiKey", apiKey);
        model.addAttribute("page", 1);
        model.addAttribute("size", 10);
        model.addAttribute("location", location);
        return "member/list";
    }

    @ResponseBody
    @RequestMapping(value = "/board/listAjax", method={RequestMethod.POST})
    public PageResult listAjax(HttpServletRequest request, Model model, PostSearch postSearch) {
        postSearch.setPage((postSearch.getPage() <= 0) ? 1 : postSearch.getPage());
        return postService.findByLocation(postSearch);
    }

    @ResponseBody
    @RequestMapping(value = "/board/addAjax", method={RequestMethod.POST})
    public BaseResult addPostAjax(HttpServletRequest request, @Valid Post post) {
        log.info(post.toString());
        post.setRecommendCnt(0L);
        post.setWriteDate(LocalDateTime.now());
        post.setDeclaredYn(false);
        return postService.save(post);
    }

    @ResponseBody
    @RequestMapping(value = "/board/recommendAjax", method = RequestMethod.POST)
    public BaseResult recommendAjax(HttpServletRequest req, HttpServletResponse resp, String id) {
        return postService.recommend(Long.parseLong(id), req.getRemoteAddr());
    }

    @ResponseBody
    @RequestMapping(value = "/board/declareAjax", method = RequestMethod.POST)
    public BaseResult declareAjax(HttpServletResponse resp, String id) {
        return postService.declare(Long.parseLong(id));
    }

}
