package com.ara.todayoutfit.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult extends BaseResult {

    private Object object;

    public SingleResult(ResponseCode responseCode) {
        super(responseCode);
    }

    @Builder
    public SingleResult(Object object) {
        super(ResponseCode.SUCCESS);
        this.object = object;
    }
}
