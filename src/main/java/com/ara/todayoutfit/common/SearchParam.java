package com.ara.todayoutfit.common;

import lombok.*;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@ToString
public class SearchParam {

    private int page;
    private int size;
    private String location;

    @Builder
    public SearchParam(int page, int size, String location) {
        this.page = page;
        this.size = size;
        this.location = location;
    }

}
