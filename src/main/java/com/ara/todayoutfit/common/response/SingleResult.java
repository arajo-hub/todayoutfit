package com.ara.todayoutfit.common.response;

import com.ara.todayoutfit.common.ResponseCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult extends BaseResult {

    private Object object;

    public SingleResult(ResponseCode responseCode) {
        super(responseCode);
    }
}
