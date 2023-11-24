package com.ara.todayoutfit.post.controller.view;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostViewController {

    @Value("${view.apiKey}")
    private String apiKey;

    /**
     * 게시판 목록 화면으로 이동
     * @param model
     * @param location
     * @return
     */
    @GetMapping("/post/list")
    public String list(Model model, String location) {
        model.addAttribute("apiKey", apiKey);
        model.addAttribute("page", 1);
        model.addAttribute("size", 10);
        model.addAttribute("location", location);
        return "post/list";
    }
    
}
