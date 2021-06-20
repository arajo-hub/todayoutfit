package com.ara.todayoutfit.board;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String content;

    @Column
    private String location;

    @Column
    private long recommendcnt;

    @Column
    @Enumerated(EnumType.STRING)
    private Declare declared;

    @Column
    private Date writedate;

    public Post() {

        declared = Declare.NOT_DECLARED;
        writedate = TimeService.getNow();

    }
}
