package com.ara.todayoutfit.recommend.service;

import com.ara.todayoutfit.common.response.BaseResult;
import com.ara.todayoutfit.common.response.ListResult;
import com.ara.todayoutfit.common.response.SingleResult;
import com.ara.todayoutfit.recommend.model.RecommendInfo;
import com.ara.todayoutfit.recommend.model.RecommendInfoUpdate;

import java.util.List;

public interface RecommendService {

    /**
     * 추천정보 전체 조회
     * @return
     */
    List<RecommendInfo> getAllRecommendInfo();

    /**
     * 기온에 따른 정보 조회
     */
    SingleResult getRecommendInfoByTemp(int temp);

    BaseResult save(RecommendInfo recommendInfo);

    BaseResult delete(Long seq);

    BaseResult update(Long seq, RecommendInfoUpdate recommendInfoUpdate);

}
