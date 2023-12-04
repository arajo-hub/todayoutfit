package com.ara.todayoutfit.recommendinfo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendInfoUpdate {

    private Integer maxTemp;
    private Integer minTemp;
    private String message;

    @Builder
    public RecommendInfoUpdate(Integer maxTemp, Integer minTemp, String message) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.message = message;
    }
}
