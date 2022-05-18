package com.ara.todayoutfit.board;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name="post")
@NoArgsConstructor
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
    private LocalDateTime writeDate;

    public Post(String content, String location) {
        this.content = content;
        this.location = location;
        this.setRecommendCnt(0);
        this.setDeclared_yn(Declare.NOT_DECLARED.getCode());
    }

    public Post(String content, String location, LocalDateTime writeDate) {
        this(content, location);
        this.setWriteDate(writeDate);
    }

}
