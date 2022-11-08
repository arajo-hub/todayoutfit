package com.ara.todayoutfit.recommend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendInfoShow {

    private String message;
    private Integer maxTemp;
    private Integer minTemp;

    @Builder
    public RecommendInfoShow(String message, Integer maxTemp, Integer minTemp) {
        this.message = message;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

}
