package com.ara.todayoutfit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WebController {

    @RequestMapping("/")
    public String jspCheck() {
        System.out.println("WebController.jspCheck");
        return "index";
    }

    @RequestMapping(value = "/board.action", method={RequestMethod.GET})
    public String goToBoard() {
        System.out.println("WebController.goToBoard");

        return "board";
    }

}
