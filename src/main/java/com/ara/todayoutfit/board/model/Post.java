package com.ara.todayoutfit.board.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name="post")
@NoArgsConstructor
public class Post {

    @Id
    @Column(name="post_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postSeq;

    @Column
    @Size(max = 30)
    private String content;

    @Column
    @Size(max = 15)
    private String location;

    @Column(name="recommend_cnt")
    private Long recommendCnt;

    @Column(name="declared_yn")
    private boolean declaredYn;

    @Column(name="write_date")
    private LocalDateTime writeDate;

    @Builder
    public Post(String content, String location, long recommendCnt, boolean declaredYn, LocalDateTime writeDate) {
        this.content = content;
        this.location = location;
        this.recommendCnt = recommendCnt;
        this.declaredYn = declaredYn;
        this.writeDate = writeDate;
    }

    public PostShow.PostShowBuilder toPostShow() {
        return PostShow.builder()
                .postSeq(postSeq)
                .content(content)
                .location(location)
                .recommendCnt(recommendCnt)
                .declaredYn(declaredYn)
                .writeDate(writeDate);
    }

}
