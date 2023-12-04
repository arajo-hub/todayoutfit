package com.ara.todayoutfit.post.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostLikeSearch {

    private Long postId;
    private String ip;

}
