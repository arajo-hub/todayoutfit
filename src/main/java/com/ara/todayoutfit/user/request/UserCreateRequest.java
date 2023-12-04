package com.ara.todayoutfit.user.request;

import com.ara.todayoutfit.user.domain.User;
import lombok.Getter;

@Getter
public class UserCreateRequest {

    private String id;
    private String pw;
    private String isAdmin;

    public User toUser() {
        return User.builder()
            .id(id)
            .pw(pw)
            .isAdmin("true".compareToIgnoreCase(isAdmin) == 0)
            .build();
    }

}
