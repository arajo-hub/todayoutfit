package com.ara.todayoutfit.recommendInfo.request;

import com.ara.todayoutfit.recommendInfo.model.RecommendInfo;
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
