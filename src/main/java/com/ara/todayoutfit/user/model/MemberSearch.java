package com.ara.todayoutfit.user.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberSearch {

    private String id;
    private String pw;

    @Builder
    public MemberSearch(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

}
