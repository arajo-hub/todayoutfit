package com.ara.todayoutfit.board.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostSearch {

    private int postSeq;
    private String content;
    private String location;
    private long recommendCnt;
    private boolean declaredYn;
    private LocalDateTime writeDate;

    @Builder
    public PostSearch(int postSeq, String content, String location, long recommendCnt, boolean declaredYn, LocalDateTime writeDate) {
        this.postSeq = postSeq;
        this.content = content;
        this.location = location;
        this.recommendCnt = recommendCnt;
        this.declaredYn = declaredYn;
        this.writeDate = writeDate;
    }

}
