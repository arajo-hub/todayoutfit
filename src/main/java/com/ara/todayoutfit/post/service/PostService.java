package com.ara.todayoutfit.post.service;

import com.ara.todayoutfit.common.ObjectResponse;
import com.ara.todayoutfit.post.domain.Post;
import com.ara.todayoutfit.post.domain.PostLike;
import com.ara.todayoutfit.post.request.PostSearch;
import com.ara.todayoutfit.post.response.PostShow;
import com.ara.todayoutfit.post.repository.PostRepository;
import com.ara.todayoutfit.post.request.PostCreateRequest;
import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.PageResponse;
import com.ara.todayoutfit.common.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PostLikeService postLikeService;

    public PageResponse<PostShow> findAll(PostSearch postSearch) {
        Page<PostShow> all = postRepository.findAll(postSearch);
        return PageResponse.<PostShow>builder()
                .list(all)
                .build();
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Optional<Post> findBySeq(Long id) {
        return postRepository.findBySeq(id);
    }

    /**
     * 위치 기반으로 게시판 글 조회
     * @param postSearch
     * @param ip
     * @return
     */
    public ObjectResponse<Page<PostShow>> findPostByLocation(PostSearch postSearch, String ip) {
        Page<PostShow> all = postRepository.findPostByLocation(postSearch);
        all.getContent().stream().forEach(postShow -> {
//            postShow.setRecommended(postLikeService.isAlreadyLiked(postShow.getPostId(), ip));
        });
        return new ObjectResponse<Page<PostShow>>(all);
    }

    /**
     * 게시글 등록
     * @param request
     * @return
     */
    public BaseResult savePost(PostCreateRequest request) {
        Post post = request.toPost();
        Post save = postRepository.save(post);
        return post.equals(save) ? new BaseResult(ResultCode.SUCCESS) : new BaseResult(ResultCode.FAIL);
    }

    public BaseResult cancelDeclare(Long seq) {
        //결과
        BaseResult result = new BaseResult(ResultCode.SUCCESS);
        postRepository.cancelDeclare(seq);
        return result;
    }

    public BaseResult saveAll(List<Post> posts) {
        List<Post> saveAll = postRepository.saveAll(posts);
        return posts.equals(saveAll) ? new BaseResult(ResultCode.SUCCESS) : new BaseResult(ResultCode.FAIL);
    }

    public BaseResult recommend(Long seq, String ip) {
        //결과
        BaseResult result = new BaseResult(ResultCode.SUCCESS);
        boolean isAlreadyRecommended = postLikeService.isAlreadyLiked(seq, ip);
        if (isAlreadyRecommended) {
            result = this.cancelRecommend(seq, ip);
        } else {
            PostLike postLike = PostLike.builder()
                            .postSeq(seq)
                            .ip(ip)
                            .build();
            postLikeService.save(postLike);
            postRepository.recommend(seq);
        }
        return result;
    }

    public BaseResult cancelRecommend(Long seq, String ip) {
        postLikeService.deleteSamePostSeqAdnIp(seq, ip);
        postRepository.cancelRecommend(seq);
        return new BaseResult(ResultCode.SUCCESS);
    }

    public BaseResult declare(Long seq) {
        //결과
        BaseResult result = new BaseResult(ResultCode.SUCCESS);
        postRepository.declare(seq);
        return result;
    }

    public BaseResult delete(Long seq) {
        postRepository.deleteBySeq(seq);
        return new BaseResult(ResultCode.SUCCESS);
    }

    public BaseResult deleteAll() {
        postRepository.deleteAll();
        return new BaseResult(ResultCode.SUCCESS);
    }
}
