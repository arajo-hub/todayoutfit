package com.ara.todayoutfit.common;

import lombok.AllArgsConstructor;
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
