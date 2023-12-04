package com.ara.todayoutfit.config.exception;

import com.ara.todayoutfit.common.ResultCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.InvalidParameterException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TodayOutfitApiException.class)
    public ResponseEntity<Object> handleCustomException(TodayOutfitApiException e) {
        ResultCode resultCode = ResultCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(resultCode.getCode()).body(resultCode.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        ResultCode resultCode = ResultCode.DB_NOT_FOUND_DATA;
        return ResponseEntity.status(resultCode.getCode()).body(resultCode.getMessage());
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Object> handleInvalidParameterException(InvalidParameterException e) {
        ResultCode resultCode = ResultCode.INVALID_PARAMETER;
        return ResponseEntity.status(resultCode.getCode()).body(resultCode.getMessage());
    }

}
