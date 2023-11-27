package com.ara.todayoutfit.post.request;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostSearch {

    private int page = 1;
    private int size = 10;
    private String location;

    @Builder
    public PostSearch(int page, int size, String location) {
        this.page = page;
        this.size = size;
        this.location = location;
    }

}
