package com.ara.todayoutfit.config.advice;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ValidationAdvice {

    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public BaseResult validationFail(BindException exception) {
        return new BaseResult(ResultCode.INVALID_PARAMETER);
    }

}