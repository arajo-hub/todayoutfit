package com.ara.todayoutfit.recommend.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RecommendInfoSearch {

    private int page;
    private int size;

    @Builder
    public RecommendInfoSearch(int page, int size) {
        this.page = page;
        this.size = size;
    }

}
