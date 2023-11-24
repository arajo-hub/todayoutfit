package com.ara.todayoutfit.recommendInfo.controller.api;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.recommendInfo.request.RecommendInfoCreateRequest;
import com.ara.todayoutfit.recommendInfo.service.RecommendInfoService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RecommendInfoController {

    private final RecommendInfoService recommendInfoService;

    @GetMapping("/recommendInfos")
    public BaseResult findRecommendInfoByTemperature(int temperature) {
        return recommendInfoService.findRecommendInfoByTemperature(temperature);
    }

    @PostMapping("/recommendInfos")
    public BaseResult saveRecommendInfo(@RequestBody RecommendInfoCreateRequest request) {
        return recommendInfoService.saveRecommendInfo(request);
    }

}
