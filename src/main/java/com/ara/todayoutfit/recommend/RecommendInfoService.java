package com.ara.todayoutfit.recommend;

import java.util.List;

public interface RecommendInfoService {

    /**
     * 추천정보 전체 조회
     * @return
     */
    List<RecommendInfo> getAllRecommendInfo();

    /**
     * 기온에 따른 정보 조회
     */
    RecommendInfo getRecommendInfoByTemp(double temp);

}
