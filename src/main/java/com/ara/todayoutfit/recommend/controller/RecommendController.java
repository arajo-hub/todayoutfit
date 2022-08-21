package com.ara.todayoutfit.recommend.controller;

import com.ara.todayoutfit.recommend.service.RecommendInfoService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class RecommendController {

    @Autowired
    private RecommendInfoService recommendInfoService;

    @RequestMapping(value = "/recommend", method={RequestMethod.GET})
    public void recommend(HttpServletRequest req, HttpServletResponse response, String temp) {

        Integer nowTemp = Integer.parseInt(temp);

        Gson gson = new Gson();

        Map<String, String> recommend = new HashMap<String, String>();

        String comment = recommendInfoService.getRecommendInfoByTemp(nowTemp).getMessage();

        if (StringUtils.isEmpty(comment)) {
            comment = "서버에 문제가 생겼습니다.";
        }
        
        recommend.put("temp", comment);

        try {

            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(gson.toJson(recommend));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
