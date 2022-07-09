package com.ara.todayoutfit.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
