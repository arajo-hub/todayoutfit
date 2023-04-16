package com.ara.todayoutfit.recommend.service.impl;

import com.ara.todayoutfit.common.response.BaseResult;
import com.ara.todayoutfit.common.ResponseCode;
import com.ara.todayoutfit.common.response.ListResult;
import com.ara.todayoutfit.common.response.SingleResult;
import com.ara.todayoutfit.recommend.exception.RecommendNotFoundException;
import com.ara.todayoutfit.recommend.model.RecommendInfo;
import com.ara.todayoutfit.recommend.model.RecommendInfoUpdate;
import com.ara.todayoutfit.recommend.repository.RecommendRepository;
import com.ara.todayoutfit.recommend.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private RecommendRepository recommendInfoRepository;

    public List<RecommendInfo> getAllRecommendInfo() {
        return recommendInfoRepository.findAll();
    }

    @Cacheable(cacheNames = "getRecommendInfoCache", key = "#temp")
    @Override
    public SingleResult getRecommendInfoByTemp(int temp) {
        RecommendInfo recommendInfo = recommendInfoRepository.findRecommendInfoByTemp(temp);
        if (recommendInfo == null) {
            throw new RecommendNotFoundException();
        }
        SingleResult result = new SingleResult(ResponseCode.SUCCESS);
        result.setData(recommendInfo);
        return result;
    }

    @Override
    public BaseResult save(RecommendInfo recommendInfo) {
        RecommendInfo save = recommendInfoRepository.save(recommendInfo);
        return (recommendInfo.equals(save)) ? new BaseResult(ResponseCode.SUCCESS) : new BaseResult(ResponseCode.FAIL);
    }

    @Override
    public BaseResult delete(Long seq) {
        recommendInfoRepository.deleteBySeq(seq);
        return new BaseResult(ResponseCode.SUCCESS);
    }

    @Override
    public BaseResult update(Long seq, RecommendInfoUpdate recommendInfoUpdate) {
        recommendInfoRepository.update(seq, recommendInfoUpdate);
        return new BaseResult(ResponseCode.SUCCESS);
    }

}
