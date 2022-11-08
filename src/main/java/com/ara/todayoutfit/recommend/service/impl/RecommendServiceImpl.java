package com.ara.todayoutfit.recommend.service.impl;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.PageResult;
import com.ara.todayoutfit.common.ResponseCode;
import com.ara.todayoutfit.recommend.model.RecommendInfo;
import com.ara.todayoutfit.recommend.model.RecommendInfoShow;
import com.ara.todayoutfit.recommend.model.RecommendInfoUpdate;
import com.ara.todayoutfit.recommend.model.RecommendInfoSearch;
import com.ara.todayoutfit.recommend.repository.RecommendInfoRepository;
import com.ara.todayoutfit.recommend.service.RecommendInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
public class RecommendServiceImpl implements RecommendInfoService {

    @Autowired
    private RecommendInfoRepository recommendInfoRepository;

    public List<RecommendInfo> getAllRecommendInfo() {
        return recommendInfoRepository.findAll();
    }

    public PageResult getAllRecommends(RecommendInfoSearch recommendSearch) {
        //결과
        PageResult result = new PageResult(ResponseCode.SUCCESS);
        Page<RecommendInfoShow> all = recommendInfoRepository.findAll(recommendSearch);
        result.setResponseCode(all.isEmpty() ? ResponseCode.DB_NOT_FOUND_DATA : result.getResponseCode());
        result.setList(all);
        return result;
    }

    @Cacheable(cacheNames = "getRecommendInfoCache", key = "#temp")
    @Override
    public RecommendInfo getRecommendInfoByTemp(Integer temp) {
        List<RecommendInfo> properRecommendInfo = recommendInfoRepository.findRecommendInfoByTemp(temp);
        return ObjectUtils.isEmpty(properRecommendInfo) ? null : properRecommendInfo.get(0);
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
