package com.ara.todayoutfit.post.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostShow {

    private Long postId;
    private String content;
    private String location;
    private Long recommendCnt;
    private boolean isRecommended;
    private boolean declaredYn;
    private LocalDateTime writeDate;
    private String writeDateStr;

}
