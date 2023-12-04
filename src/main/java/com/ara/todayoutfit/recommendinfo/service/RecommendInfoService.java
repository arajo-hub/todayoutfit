package com.ara.todayoutfit.recommendinfo.service;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.ResultCode;
import com.ara.todayoutfit.common.ObjectResponse;
import com.ara.todayoutfit.recommendinfo.model.RecommendInfo;
import com.ara.todayoutfit.recommendinfo.model.RecommendInfoUpdate;
import com.ara.todayoutfit.recommendinfo.repository.RecommendInfoRepository;
import com.ara.todayoutfit.recommendinfo.request.RecommendInfoCreateRequest;
import com.ara.todayoutfit.recommendinfo.response.RecommendInfoShow;
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
    public ObjectResponse<RecommendInfoShow> findRecommendInfoByTemperature(int temperature) {
        RecommendInfo recommendInfo = recommendInfoRepository.findRecommendInfoByTemperature(temperature);
        RecommendInfoShow recommendInfoShow = recommendInfo.toRecommendInfoShow();
        return ObjectResponse.<RecommendInfoShow>builder().object(recommendInfoShow).build();
    }

    public ObjectResponse<RecommendInfoShow> saveRecommendInfo(RecommendInfoCreateRequest request) {
        RecommendInfo recommendInfo = request.toRecommendInfo();
        RecommendInfo savedInfo = recommendInfoRepository.saveRecommendInfo(recommendInfo);
        return ObjectResponse.<RecommendInfoShow>builder().object(savedInfo.toRecommendInfoShow()).build();
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
