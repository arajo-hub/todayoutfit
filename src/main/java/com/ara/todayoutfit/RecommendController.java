package com.ara.todayoutfit;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RecommendController {

    @RequestMapping(value = "/recommend.action", method={RequestMethod.GET})
    public void recommend(HttpServletRequest req, HttpServletResponse response, String temp) {

        System.out.println("RecommendController.recommend");

        double nowTemp = Double.parseDouble(temp);

        Gson gson = new Gson();

        Map<String, String> recommend = new HashMap<String, String>();

        String comment = getComment(nowTemp);
        recommend.put("temp", comment);

        try {

            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(gson.toJson(recommend));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getComment(double nowTemp) {

        // temp에 따라 다른 문구 반환
        String comment;

        if (nowTemp <= 4) {
            comment = "날씨가 추우니 패딩, 두꺼운 코트, 목도리, 기모제품을 추천합니다.";
        } else if (nowTemp >= 5 && nowTemp <= 8) {
            comment = "코트, 히트텍, 니트, 청바지, 레깅스를 추천합니다.";
        } else if (nowTemp >= 9 && nowTemp <= 11) {
            comment = "아직은 쌀쌀하니 자켓, 트렌치코트, 야상, 니트, 스타킹, 청바지, 면바지를 추천합니다.";
        } else if (nowTemp >= 12 && nowTemp <= 16) {
            comment = "자켓, 가디건, 야상, 맨투맨, 니트, 스타킹, 청바지, 면바지를 추천합니다.";
        } else if (nowTemp >= 17 && nowTemp <= 19) {
            comment = "얇은 니트, 가디건, 얇은 자켓, 맨투맨, 면바지, 청바지를 추천합니다.";
        } else if (nowTemp >= 20 && nowTemp <= 22) {
            comment = "얇은 가디건, 긴팔티, 면바지, 청바지를 추천합니다.";
        } else if (nowTemp >= 23 && nowTemp <= 27) {
            comment = "반팔, 얇은 셔츠, 반바지, 면바지를 추천합니다.";
        } else {
            comment = "민소매, 반팔, 반바지, 치마를 추천합니다.";
        }

        return comment;

    }

}
