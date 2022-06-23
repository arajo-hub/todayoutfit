package com.ara.todayoutfit.recommend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<RecommendInfo> properRecommendInfo = recommendInfoRepository.findOne(RecommendInfoSpecifications.findMin(temp)
                                                                                .and(RecommendInfoSpecifications.findMax(temp)));
        long end = System.currentTimeMillis();
        log.info("수행시간" + Long.toString(end - start));
        return properRecommendInfo.isPresent()? properRecommendInfo.get() : new RecommendInfo();
    }

}
