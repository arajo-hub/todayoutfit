package com.ara.todayoutfit.post.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostLikeMultiSearch {

    private List<Long> postIds;
    private String ip;

}
