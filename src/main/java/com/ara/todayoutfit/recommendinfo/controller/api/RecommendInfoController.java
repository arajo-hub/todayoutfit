package com.ara.todayoutfit.recommendinfo.controller.api;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.ObjectResponse;
import com.ara.todayoutfit.recommendinfo.model.RecommendInfo;
import com.ara.todayoutfit.recommendinfo.model.RecommendInfoUpdate;
import com.ara.todayoutfit.recommendinfo.request.RecommendInfoCreateRequest;
import com.ara.todayoutfit.recommendinfo.service.RecommendInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RecommendInfoController {

    private final RecommendInfoService recommendInfoService;

    /**
     * 추천 정보 전체 조회
     * @return
     */
    @GetMapping("/recommendInfos")
    public ObjectResponse<List<RecommendInfo>> findAllRecommendInfo() {
        return recommendInfoService.findAllRecommendInfo();
    }

    /**
     * 추천 정보 상세 조회
     * @param recommendInfoSeq
     * @return
     */
    @GetMapping("/recommendInfos/{recommendInfoSeq}")
    public ObjectResponse<RecommendInfo> findRecommendInfoBySeq(@PathVariable Long recommendInfoSeq) {
        return recommendInfoService.findRecommendInfoBySeq(recommendInfoSeq);
    }

    /**
     * 기온에 따른 추천 정보 조회
     * @param temperature
     * @return
     */
    @GetMapping("/recommendInfos/temperature/{temperature}")
    public BaseResult findRecommendInfoByTemperature(int temperature) {
        return recommendInfoService.findRecommendInfoByTemperature(temperature);
    }

    /**
     * 추천 정보 등록
     * @param request
     * @return
     */
    @PostMapping("/recommendInfos")
    public BaseResult saveRecommendInfo(@RequestBody RecommendInfoCreateRequest request) {
        return recommendInfoService.saveRecommendInfo(request);
    }

    /**
     * 추천 정보 수정
     */
    @PutMapping("/recommendInfos/{recommendInfoSeq}")
    public BaseResult updateRecommendInfo(@PathVariable Long recommendInfoSeq, @RequestBody RecommendInfoUpdate recommendInfo) {
        return recommendInfoService.update(recommendInfoSeq, recommendInfo);
    }

    /**
     * 추천 정보 삭제
     */
    @DeleteMapping("/recommendInfos/{recommendInfoSeq}")
    public BaseResult deleteRecommendInfo(@PathVariable Long recommendInfoSeq) {
        return recommendInfoService.delete(recommendInfoSeq);
    }

}
