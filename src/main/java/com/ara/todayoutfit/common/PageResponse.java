package com.ara.todayoutfit.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Builder
public class PageResponse<T> extends BaseResult {

    private Page<T> list;

    public PageResponse(ResultCode responseCode) {
        super(responseCode);
    }
}
