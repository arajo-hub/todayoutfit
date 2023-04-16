package com.ara.todayoutfit.recommend.controller;

import com.ara.todayoutfit.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.ara.todayoutfit.common.response.ListResult;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendInfoService;

    @GetMapping(value = "/recommend")
    public ListResult recommend(int temp) {
        return recommendInfoService.getRecommendInfoByTemp(temp);
    }

}
