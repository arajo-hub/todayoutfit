package com.ara.todayoutfit.member.controller;

import com.ara.todayoutfit.post.service.PostService;
import com.ara.todayoutfit.common.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
