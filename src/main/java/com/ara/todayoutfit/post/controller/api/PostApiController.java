package com.ara.todayoutfit.post.controller.api;

import com.ara.todayoutfit.post.request.PostSearch;
import com.ara.todayoutfit.post.request.PostCreateRequest;
import com.ara.todayoutfit.post.response.PostShow;
import com.ara.todayoutfit.post.service.PostService;
import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    /**
     * 기온에 따라 게시글 목록 조회
     * @param request
     * @param search
     * @return
     */
    @GetMapping("/posts")
    public PageResponse<PostShow> posts(HttpServletRequest request, PostSearch search) {
        return postService.findPostByLocation(search, request.getRemoteAddr());
    }

    /**
     * 관리자용 게시글 목록 조회
     * @param search
     * @return
     */
    @GetMapping("/admin/posts")
    public PageResponse<PostShow> postsForAdmin(PostSearch search) {
        return postService.findAll(search);
    }

    /**
     * 게시글 등록
     * @param request
     * @return
     */
    @PostMapping("/posts")
    public BaseResult savePost(@RequestBody @Valid PostCreateRequest request) {
        return postService.savePost(request);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/posts/{postId}")
    public BaseResult deletePost(Long postId) {
        return postService.deletePost(postId);
    }

    /**
     * 게시글 신고
     */
    @PostMapping("/posts/{postId}/declare")
    public BaseResult declarePost(@PathVariable Long postId) {
        return postService.declarePost(postId);
    }

    /**
     * 게시글 신고 취소
     */
    @PostMapping("/posts/{postId}/declare/cancel")
    public BaseResult cancelDeclarePost(@PathVariable Long postId) {
        return postService.cancelDeclare(postId);
    }

    /**
     * 게시글 좋아요 기능
     */
    @PostMapping("/posts/{postId}/like")
    public BaseResult likePost(@PathVariable Long postId, HttpServletRequest request) {
        return postService.likePost(postId, request.getRemoteAddr());
    }

}
