package com.ara.todayoutfit.admin;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="admin")
@SequenceGenerator(
        name = "ADMIN_SEQ_GENERATOR",
        sequenceName = "seqadmin",
        initialValue = 1, allocationSize = 1)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "ADMIN_SEQ_GENERATOR")
    private long id;

    @Column
    private String name;

    @Column
    private String pw;

}
