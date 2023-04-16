package com.ara.todayoutfit.common.response;

import com.ara.todayoutfit.common.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseResult {

    private ResponseCode responseCode;

    public boolean isSuccess() {
        return this.responseCode.getCode().equals(ResponseCode.SUCCESS.getCode());
    }

    public boolean isNotSuccess() {
        return !isSuccess();
    }

}
