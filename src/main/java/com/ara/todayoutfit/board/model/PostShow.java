package com.ara.todayoutfit.board.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class PostShow {

    private Long postSeq;
    private String content;
    private String location;
    private Long recommendCnt;
    private boolean isRecommended;
    private boolean declaredYn;
    private LocalDateTime writeDate;
    private String writeDateStr;

    @Builder
    public PostShow(Long postSeq, String content, String location, Long recommendCnt, boolean isRecommended, boolean declaredYn, LocalDateTime writeDate) {
        this.postSeq = postSeq;
        this.content = content;
        this.location = location;
        this.recommendCnt = recommendCnt;
        this.isRecommended = isRecommended;
        this.declaredYn = declaredYn;
        this.writeDate = writeDate;
        this.writeDateStr = this.writeDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
    }

}
