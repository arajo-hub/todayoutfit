package com.ara.todayoutfit.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PageResult<T> extends BaseResult {

    private Page<T> list;

    public PageResult(ResponseCode responseCode) {
        super(responseCode);
    }
}
