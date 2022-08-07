package com.ara.todayoutfit.common;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class SearchParam {

    private int page;
    private int size;
    private String location;

}
