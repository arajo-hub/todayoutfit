package com.ara.todayoutfit.user.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserShow {

    private String id;
    private boolean isAdmin;

}
