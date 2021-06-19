package com.ara.todayoutfit.board;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="post")
@SequenceGenerator(
        name = "POST_SEQ_GENERATOR",
        sequenceName = "seqpost",
        initialValue = 1, allocationSize = 1)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "POST_SEQ_GENERATOR")
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

    @Column
    private Date writedate;

    public Post() {

        declare = Declare.NOT_DECLARED;
        writedate = TimeService.getNow();

    }
}
