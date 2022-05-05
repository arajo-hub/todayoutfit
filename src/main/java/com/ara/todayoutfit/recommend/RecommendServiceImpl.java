package com.ara.todayoutfit.recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendServiceImpl implements RecommendInfoService {

    @Autowired
    private RecommendInfoRepository recommendInfoRepository;

    public List<RecommendInfo> getAllRecommendInfo() {
        return recommendInfoRepository.findAll();
    }

    @Override
    public RecommendInfo getRecommendInfoByTemp(double temp) {
        Optional<RecommendInfo> properRecommendInfo = recommendInfoRepository.findOne(RecommendInfoSpecifications.findMin(temp)
                                                                                .and(RecommendInfoSpecifications.findMax(temp)));
        return properRecommendInfo.isPresent()? properRecommendInfo.get() : new RecommendInfo();
    }

}
