package com.ara.todayoutfit.board;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="post")
public class Post {

    @Id
    @Column(name="post_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postSeq;

    @Column
    private String content;

    @Column
    private String location;

    @Column(name="recommend_cnt")
    private long recommendCnt;

    @Column
    private String declared_yn;

    @Column(name="write_date")
    private Date writeDate;

    public Post() {

        declared_yn = Declare.NOT_DECLARED.getCode();
        writeDate = TimeService.getNow();

    }
}
