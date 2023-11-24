package com.ara.todayoutfit.user.service.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserSearch {

    private String id;
    private String pw;

    @Builder
    public UserSearch(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

}
