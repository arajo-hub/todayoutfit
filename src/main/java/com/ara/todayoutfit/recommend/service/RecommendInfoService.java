package com.ara.todayoutfit.recommend.service;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.PageResult;
import com.ara.todayoutfit.recommend.model.RecommendInfo;
import com.ara.todayoutfit.recommend.model.RecommendInfoUpdate;
import com.ara.todayoutfit.recommend.model.RecommendInfoSearch;

import java.util.List;

public interface RecommendInfoService {

    /**
     * 추천정보 전체 조회
     * @return
     */
    List<RecommendInfo> getAllRecommendInfo();

    /**
     * 추천정보 페이지로 조회
     */
    PageResult getAllRecommends(RecommendInfoSearch recommendSearch);

    /**
     * 기온에 따른 정보 조회
     */
    RecommendInfo getRecommendInfoByTemp(Integer temp);

    BaseResult save(RecommendInfo recommendInfo);

    BaseResult delete(Long seq);

    BaseResult update(Long seq, RecommendInfoUpdate recommendInfoUpdate);

}
