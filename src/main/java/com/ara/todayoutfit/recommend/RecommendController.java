package com.ara.todayoutfit.recommend;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RecommendController {

    @Autowired
    private RecommendInfoService recommendInfoService;

    @RequestMapping(value = "/recommend", method={RequestMethod.GET})
    public void recommend(HttpServletRequest req, HttpServletResponse response, String temp) {

        double nowTemp = Double.parseDouble(temp);

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
