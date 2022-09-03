package com.ara.todayoutfit.board.service.impl;

import com.ara.todayoutfit.board.model.Post;
import com.ara.todayoutfit.board.model.PostLike;
import com.ara.todayoutfit.board.model.PostSearch;
import com.ara.todayoutfit.board.model.PostShow;
import com.ara.todayoutfit.board.repository.PostRepository;
import com.ara.todayoutfit.board.service.PostLikeService;
import com.ara.todayoutfit.board.service.PostService;
import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.PageResult;
import com.ara.todayoutfit.common.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeService postLikeService;

    public PageResult findAll(PostSearch postSearch) {
        //결과
        PageResult result = new PageResult(ResponseCode.SUCCESS);
        Page<PostShow> all = postRepository.findAll(postSearch);
        result.setResponseCode(all.isEmpty() ? ResponseCode.DB_NOT_FOUND_DATA : result.getResponseCode());
        result.setList(all);
        return result;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Optional<Post> findBySeq(Long id) {
        return postRepository.findBySeq(id);
    }

    public PageResult findByLocation(PostSearch postSearch, String ip) {
        //결과
        PageResult result = new PageResult(ResponseCode.SUCCESS);
        Page<PostShow> all = postRepository.findByLocation(postSearch);
        all.getContent().stream().forEach(postShow -> {
            postShow.setRecommended(postLikeService.isAlreadyLiked(postShow.getPostSeq(), ip));
        });
        result.setResponseCode(all.isEmpty() ? ResponseCode.DB_NOT_FOUND_DATA : result.getResponseCode());
        result.setList(all);
        return result;
    }

    public BaseResult cancelDeclare(Long seq) {
        //결과
        BaseResult result = new BaseResult(ResponseCode.SUCCESS);
        postRepository.cancelDeclare(seq);
        log.info("[{}] {}", Thread.currentThread().getStackTrace()[1].getMethodName(), result.getResponseCode().getMessage());
        return result;
    }

    @Override
    public BaseResult save(Post post) {
        Post save = postRepository.save(post);
        return post.equals(save) ? new BaseResult(ResponseCode.SUCCESS) : new BaseResult(ResponseCode.FAIL);
    }

    @Override
    public BaseResult saveAll(List<Post> posts) {
        List<Post> saveAll = postRepository.saveAll(posts);
        return posts.equals(saveAll) ? new BaseResult(ResponseCode.SUCCESS) : new BaseResult(ResponseCode.FAIL);
    }

    @Override
    public BaseResult recommend(Long seq, String ip) {
        //결과
        BaseResult result = new BaseResult(ResponseCode.SUCCESS);
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
        log.info("[{}] {}", Thread.currentThread().getStackTrace()[1].getMethodName(), result.getResponseCode().getMessage());
        return result;
    }

    @Override
    public BaseResult cancelRecommend(Long seq, String ip) {
        postLikeService.deleteSamePostSeqAdnIp(seq, ip);
        postRepository.cancelRecommend(seq);
        return new BaseResult(ResponseCode.SUCCESS);
    }

    @Override
    public BaseResult declare(Long seq) {
        //결과
        BaseResult result = new BaseResult(ResponseCode.SUCCESS);
        postRepository.declare(seq);
        log.info("[{}] {}", Thread.currentThread().getStackTrace()[1].getMethodName(), result.getResponseCode().getMessage());
        return result;
    }

    @Override
    public BaseResult delete(Long seq) {
        postRepository.deleteBySeq(seq);
        return new BaseResult(ResponseCode.SUCCESS);
    }

    @Override
    public BaseResult deleteAll() {
        postRepository.deleteAll();
        return new BaseResult(ResponseCode.SUCCESS);
    }

}
