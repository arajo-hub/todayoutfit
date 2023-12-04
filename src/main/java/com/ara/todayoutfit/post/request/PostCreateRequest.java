package com.ara.todayoutfit.post.request;

import com.ara.todayoutfit.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Builder
public class PostCreateRequest {

    @NotEmpty
    @Size(min = 1, max = 50)
    private String content;
    @NotEmpty
    @Size(min = 1, max = 15)
    private String location;

    public Post toPost() {
        return Post.builder()
                .content(content)
                .location(location)
                .build();
    }

}
