package com.ara.todayoutfit.recommendinfo.request;

import com.ara.todayoutfit.recommendinfo.model.RecommendInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecommendInfoCreateRequest {

    private Integer maxTemperature;
    private Integer minTemperature;
    private String message;

    public RecommendInfo toRecommendInfo() {
        return RecommendInfo.builder()
                .maxTemp(maxTemperature)
                .minTemp(minTemperature)
                .message(message)
                .build();
    }

}
