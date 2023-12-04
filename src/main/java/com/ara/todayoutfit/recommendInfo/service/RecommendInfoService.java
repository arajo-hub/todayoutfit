package com.ara.todayoutfit.recommendInfo.service;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.PageResponse;
import com.ara.todayoutfit.common.ResultCode;
import com.ara.todayoutfit.common.ObjectResponse;
import com.ara.todayoutfit.recommendInfo.model.RecommendInfo;
import com.ara.todayoutfit.recommendInfo.model.RecommendInfoUpdate;
import com.ara.todayoutfit.recommendInfo.repository.RecommendInfoRepository;
import com.ara.todayoutfit.recommendInfo.request.RecommendInfoCreateRequest;
import com.ara.todayoutfit.recommendInfo.response.RecommendInfoShow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendInfoService {

    private final RecommendInfoRepository recommendInfoRepository;

    public List<RecommendInfo> getAllRecommendInfo() {
        return recommendInfoRepository.findAll();
    }

//    @Cacheable(cacheNames = "getRecommendInfoCache", key = "#temp")
    public ObjectResponse findRecommendInfoByTemperature(int temperature) {
        RecommendInfo recommendInfo = recommendInfoRepository.findRecommendInfoByTemperature(temperature);
        RecommendInfoShow recommendInfoShow = recommendInfo.toRecommendInfoShow();
        return ObjectResponse.builder().object(recommendInfoShow).build();
    }

    public BaseResult saveRecommendInfo(RecommendInfoCreateRequest request) {
        RecommendInfo recommendInfo = request.toRecommendInfo();
        RecommendInfo savedInfo = recommendInfoRepository.saveRecommendInfo(recommendInfo);
        return new ObjectResponse(savedInfo);
    }

    public BaseResult delete(Long seq) {
        recommendInfoRepository.deleteBySeq(seq);
        return new BaseResult(ResultCode.SUCCESS);
    }

    public BaseResult update(Long seq, RecommendInfoUpdate recommendInfoUpdate) {
        recommendInfoRepository.update(seq, recommendInfoUpdate);
        return new BaseResult(ResultCode.SUCCESS);
    }

    public ObjectResponse<List<RecommendInfo>> findAllRecommendInfo() {
        List<RecommendInfo> recommendInfoList = recommendInfoRepository.findAll();
        return new ObjectResponse<List<RecommendInfo>>(recommendInfoList);
    }

    public ObjectResponse<RecommendInfo> findRecommendInfoBySeq(Long recommendInfoSeq) {
        Optional<RecommendInfo> recommendInfo = recommendInfoRepository.findBySeq(recommendInfoSeq);

        if (!recommendInfo.isPresent()) {
            return new ObjectResponse(ResultCode.DB_NOT_FOUND_DATA);
        }

        return new ObjectResponse<>(recommendInfo.get());
    }
}
