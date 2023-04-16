package com.ara.todayoutfit.recommend.controller;

import com.ara.todayoutfit.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.ara.todayoutfit.common.response.SingleResult;

@Controller
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendInfoService;

    @GetMapping(value = "/recommend")
    public SingleResult recommend(int temp) {
        return recommendInfoService.getRecommendInfoByTemp(temp);
    }

}
