package com.ara.todayoutfit.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectResponse<T> extends BaseResult {

    private T object;

    @Builder
    public ObjectResponse(T object) {
        super(ResultCode.SUCCESS);
        this.object = object;
    }

}
