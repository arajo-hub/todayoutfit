package com.ara.todayoutfit.member.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name="member")
@NoArgsConstructor
public class Member {

    @Id
    @Size(max = 10)
    private String id;

    @Column
    @Size(max = 30)
    private String pw;

    @Column(name="is_admin")
    private boolean isAdmin;

    @Builder
    public Member(String id, String pw, boolean isAdmin) {
        this.id = id;
        this.pw = pw;
        this.isAdmin = isAdmin;
    }

}
