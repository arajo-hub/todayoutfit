package com.ara.todayoutfit.user.domain;

import com.ara.todayoutfit.user.response.UserShow;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@Entity
@Table(name="users")
@NoArgsConstructor
public class User {

    @Id
    @Size(max = 10)
    private String id;

    @Column
    @Size(max = 150)
    private String pw;

    @Column(name="is_admin")
    private boolean isAdmin;

    @Builder
    public User(String id, String pw, boolean isAdmin) {
        this.id = id;
        this.pw = pw;
        this.isAdmin = isAdmin;
    }

    public UserShow toUserShow() {
        return UserShow.builder()
            .id(id)
            .isAdmin(isAdmin)
            .build();
    }

}
