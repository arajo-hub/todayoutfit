package com.ara.todayoutfit.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseResult {

    private ResultCode resultCode;

    public boolean isSuccess() {
        return this.resultCode.equals(ResultCode.SUCCESS);
    }

    public boolean isNotSuccess() {
        return !isSuccess();
    }

}
