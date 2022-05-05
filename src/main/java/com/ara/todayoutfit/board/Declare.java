package com.ara.todayoutfit.board;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum Declare {

    NOT_DECLARED("0", "NOT_DECLARED"),
    DECLARED("1", "DECLARED");

    private final String code;
    private final String message;

    Declare(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
