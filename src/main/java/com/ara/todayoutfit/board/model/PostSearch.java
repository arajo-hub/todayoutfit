package com.ara.todayoutfit.board.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostSearch {

    private int page;
    private int size;
    private String location;

    @Builder
    public PostSearch(int page, int size, String location) {
        this.page = page;
        this.size = size;
        this.location = location;
    }

}
