package com.ara.todayoutfit.board;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column
    private String content;

    @Column
    private String location;

    @Column
    private long recommendcnt;

    @Column
    @Enumerated(EnumType.STRING)
    private Declare declare;

}
