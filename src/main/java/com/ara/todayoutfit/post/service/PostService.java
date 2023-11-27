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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PostLikeService postLikeService;

    /**
     * 게시글 전체 조회 with paging
     * @param postSearch
     * @return
     */
    public PageResponse<PostShow> findPostWithPaging(PostSearch postSearch) {
        Pageable pageable = PageRequest.of(postSearch.getPage() - 1, postSearch.getSize());
        List<Post> allPosts = postRepository.findAll(postSearch, pageable);
        Long count = postRepository.getCount(postSearch.getLocation());

        List<PostShow> postShows = new ArrayList<PostShow>();
        allPosts.forEach(p -> postShows.add(p.toPostShow()));
        Page<PostShow> postShowPages = new PageImpl<>(postShows, pageable, count);
        return new PageResponse<PostShow>(postShowPages);
    }

    /**
     * 게시글 전체 조회
     * @return
     */
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    /**
     * 위치 기반으로 게시판 글 조회
     * @param postSearch
     * @param ip
     * @return
     */
    public PageResponse<PostShow> findPostByLocationWithPaging(PostSearch postSearch, String ip) {
        Pageable pageable = PageRequest.of(postSearch.getPage(), postSearch.getSize());
        List<Post> allPosts = postRepository.findPostByLocation(postSearch, pageable);
        Long count = postRepository.getCount(postSearch.getLocation());

        List<PostShow> postShows = new ArrayList<PostShow>();
        allPosts.forEach(p -> postShows.add(p.toPostShow()));

        Page<PostShow> postShowPages = new PageImpl<>(postShows, pageable, count);
        return new PageResponse<PostShow>(postShowPages);
    }

    /**
     * 게시글 등록
     * @param request
     * @return
     */
    public ObjectResponse<PostShow> savePost(PostCreateRequest request) {
        Post save = postRepository.save(request.toPost());
        return new ObjectResponse<PostShow>(save.toPostShow());
    }

    /**
     * 게시글 추천
     * @param seq
     * @param ip
     * @return
     */
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

    /**
     * 게시글 추천 취소
     * @param seq
     * @param ip
     * @return
     */
    public BaseResult cancelRecommend(Long seq, String ip) {
        postLikeService.deleteSamePostSeqAdnIp(seq, ip);
        postRepository.cancelRecommend(seq);
        return new BaseResult(ResultCode.SUCCESS);
    }

    /**
     * 게시글 신고
     * @param seq
     * @return
     */
    public BaseResult declare(Long seq) {
        //결과
        BaseResult result = new BaseResult(ResultCode.SUCCESS);
        postRepository.declare(seq);
        return result;
    }

    /**
     * 게시글 신고 취소
     * @param seq
     * @return
     */
    public BaseResult cancelDeclare(Long seq) {
        //결과
        BaseResult result = new BaseResult(ResultCode.SUCCESS);
        postRepository.cancelDeclare(seq);
        return result;
    }

    /**
     * 게시글 삭제
     * @param seq
     * @return
     */
    public BaseResult delete(Long seq) {
        postRepository.deleteBySeq(seq);
        return new BaseResult(ResultCode.SUCCESS);
    }

    /**
     * 게시글 전체 삭제
     * @return
     */
    public BaseResult deleteAll() {
        postRepository.deleteAll();
        return new BaseResult(ResultCode.SUCCESS);
    }
}
