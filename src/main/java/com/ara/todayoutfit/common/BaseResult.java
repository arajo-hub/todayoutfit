package com.ara.todayoutfit.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseResult {

    private ResponseCode responseCode;

    public boolean isSuccess() {
        return this.responseCode.getCode().equals(ResponseCode.SUCCESS.getCode()) ? true : false;
    }

    public boolean isNotSuccess() {
        return !isSuccess();
    }

}
