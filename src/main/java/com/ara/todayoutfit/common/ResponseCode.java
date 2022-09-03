package com.ara.todayoutfit.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS("0000", "success"),
    FAIL("9999", "fail"),

    INVALID_PARAMETER("5000", "invalid parameter"),
    DB_NOT_FOUND_DATA("5001", "db_not_found_data"),

    WRONG_PASSWORD("6000", "wrong password"),
    ALREADY_LIKED("6001", "already liked"),
    ;

    private String code;
    private String message;

}
