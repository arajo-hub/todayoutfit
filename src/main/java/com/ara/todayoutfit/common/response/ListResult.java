package com.ara.todayoutfit.common.response;

import com.ara.todayoutfit.common.ResponseCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResult<T> extends BaseResult {

    private List<T> list;

    public ListResult(ResponseCode responseCode) {
        super(responseCode);
    }

}
