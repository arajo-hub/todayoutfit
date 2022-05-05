package com.ara.todayoutfit.admin;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="user")
public class Admin {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String pw;

    @Column
    private String email;

}
