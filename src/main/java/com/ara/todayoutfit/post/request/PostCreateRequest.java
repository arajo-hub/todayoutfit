package com.ara.todayoutfit.post.request;

import com.ara.todayoutfit.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequest {

    private String content;
    private String location;

    public Post toPost() {
        return Post.builder()
                .content(content)
                .location(location)
                .build();
    }

}
