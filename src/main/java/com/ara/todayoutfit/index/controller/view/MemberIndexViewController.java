package com.ara.todayoutfit.index.controller.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class MemberIndexViewController {

    @Value("${view.apiKey}")
    private String apiKey;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("apiKey", apiKey);
        return "member/index";
    }

}
