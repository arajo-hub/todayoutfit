package com.ara.todayoutfit.post.controller.api;

import com.ara.todayoutfit.post.request.PostSearch;
import com.ara.todayoutfit.post.request.PostCreateRequest;
import com.ara.todayoutfit.post.service.PostService;
import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    /**
     * 게시글 목록 조회
     * @param request
     * @param search
     * @return
     */
    @GetMapping("/posts")
    public PageResult boards(HttpServletRequest request, PostSearch search) {
        return postService.findPostByLocation(search, request.getRemoteAddr());
    }

    /**
     * 게시글 등록
     * @param request
     * @return
     */
    @PostMapping("/posts")
    public BaseResult savePost(PostCreateRequest request) {
        return postService.savePost(request);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/posts/{postId}")
    public BaseResult deletePost(Long postId) {
        return postService.delete(postId);
    }

}
