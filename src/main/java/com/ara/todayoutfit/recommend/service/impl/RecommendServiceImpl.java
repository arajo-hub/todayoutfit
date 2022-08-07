package com.ara.todayoutfit.recommend.service.impl;

import com.ara.todayoutfit.recommend.model.RecommendInfo;
import com.ara.todayoutfit.recommend.repository.RecommendInfoRepository;
import com.ara.todayoutfit.recommend.service.RecommendInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(cacheNames = "getRecommendInfoCache", key = "#temp")
    @Override
    public RecommendInfo getRecommendInfoByTemp(double temp) {
        long start = System.currentTimeMillis();
        List<RecommendInfo> properRecommendInfo = recommendInfoRepository.findRecommendInfoByTemp(temp);
        long end = System.currentTimeMillis();
        log.info("수행시간" + Long.toString(end - start));
        return ObjectUtils.isEmpty(properRecommendInfo) ? null : properRecommendInfo.get(0);
    }

}
