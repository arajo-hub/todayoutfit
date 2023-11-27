package com.ara.todayoutfit.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(HttpStatus.OK.value(), "Success"),
    FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Fail"),

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST.value(), "invalid parameter"),
    DB_NOT_FOUND_DATA(HttpStatus.NOT_FOUND.value(), "db_not_found_data"),

    DUPLICATED_REQUEST(HttpStatus.TOO_MANY_REQUESTS.value(), "duplicated request"),
    ;

    private int code;
    private String message;

}
