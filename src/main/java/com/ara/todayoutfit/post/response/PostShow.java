package com.ara.todayoutfit.post.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
public class PostShow {

    private Long postId;
    private String content;
    private String location;
    private Long recommendCnt;
    private boolean likedYn;
    private boolean declaredYn;
    private LocalDateTime writeDate;

    public String getWriteDateToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return writeDate.format(formatter);
    }
}
