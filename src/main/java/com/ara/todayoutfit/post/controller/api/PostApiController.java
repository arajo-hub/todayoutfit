package com.ara.todayoutfit.post.controller.api;

import com.ara.todayoutfit.common.ObjectResponse;
import com.ara.todayoutfit.post.request.PostSearch;
import com.ara.todayoutfit.post.request.PostCreateRequest;
import com.ara.todayoutfit.post.response.PostShow;
import com.ara.todayoutfit.post.service.PostService;
import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public PageResponse<PostShow> boards(HttpServletRequest request, PostSearch search) {
        return postService.findPostByLocation(search, request.getRemoteAddr());
    }

    /**
     * 게시글 등록
     * @param request
     * @return
     */
    @PostMapping("/posts")
    public BaseResult savePost(@RequestBody PostCreateRequest request) {
        return postService.savePost(request);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/posts/{postId}")
    public BaseResult deletePost(Long postId) {
        return postService.delete(postId);
    }

    /**
     * 게시글 신고
     */
    @PostMapping("/posts/{postId}/declare")
    public BaseResult declarePost(@PathVariable Long postId) {
        return postService.declare(postId);
    }

    /**
     * 게시글 신고 취소
     */
    @PostMapping("/posts/{postId}/declare/cancel")
    public BaseResult cancelDeclarePost(@PathVariable Long postId) {
        return postService.cancelDeclare(postId);
    }

}
