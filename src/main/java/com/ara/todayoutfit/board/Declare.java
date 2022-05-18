package com.ara.todayoutfit.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum Declare {

    NOT_DECLARED("0", "NOT_DECLARED"),
    DECLARED("1", "DECLARED")
    ;

    private final String code;
    private final String message;

}
