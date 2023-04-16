package com.ara.todayoutfit.common.response;

import com.ara.todayoutfit.common.ResponseCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class PageResult<T> extends BaseResult {

    private Page<T> list;

    public PageResult(ResponseCode responseCode) {
        super(responseCode);
    }
}
