package com.ara.todayoutfit.recommendInfo.service;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.ResponseCode;
import com.ara.todayoutfit.common.SingleResult;
import com.ara.todayoutfit.recommendInfo.model.RecommendInfo;
import com.ara.todayoutfit.recommendInfo.model.RecommendInfoUpdate;
import com.ara.todayoutfit.recommendInfo.repository.RecommendInfoRepository;
import com.ara.todayoutfit.recommendInfo.request.RecommendInfoCreateRequest;
import com.ara.todayoutfit.recommendInfo.response.RecommendInfoShow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendInfoService {

    private final RecommendInfoRepository recommendInfoRepository;

    public List<RecommendInfo> getAllRecommendInfo() {
        return recommendInfoRepository.findAll();
    }

//    @Cacheable(cacheNames = "getRecommendInfoCache", key = "#temp")
    public SingleResult findRecommendInfoByTemperature(int temperature) {
        RecommendInfo recommendInfo = recommendInfoRepository.findRecommendInfoByTemperature(temperature);
        RecommendInfoShow recommendInfoShow = recommendInfo.toRecommendInfoShow();
        return SingleResult.builder().object(recommendInfoShow).build();
    }

    public BaseResult saveRecommendInfo(RecommendInfoCreateRequest request) {
        RecommendInfo recommendInfo = request.toRecommendInfo();
        RecommendInfo savedInfo = recommendInfoRepository.saveRecommendInfo(recommendInfo);
        return new SingleResult(savedInfo);
    }

    public BaseResult delete(Long seq) {
        recommendInfoRepository.deleteBySeq(seq);
        return new BaseResult(ResponseCode.SUCCESS);
    }

    public BaseResult update(Long seq, RecommendInfoUpdate recommendInfoUpdate) {
        recommendInfoRepository.update(seq, recommendInfoUpdate);
        return new BaseResult(ResponseCode.SUCCESS);
    }
}
