package com.ara.todayoutfit.board.service.impl;

import com.ara.todayoutfit.board.model.Declare;
import com.ara.todayoutfit.board.model.Post;
import com.ara.todayoutfit.board.repository.PostRepository;
import com.ara.todayoutfit.board.repository.PostSpecifications;
import com.ara.todayoutfit.board.service.PostService;
import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.PageResult;
import com.ara.todayoutfit.common.ResponseCode;
import com.ara.todayoutfit.common.SearchParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    public PageResult list(SearchParam searchParam) {
        //결과
        PageResult result = new PageResult(ResponseCode.SUCCESS);
        Pageable pageable = PageRequest.of(searchParam.getPage() - 1, searchParam.getSize(), Sort.by("writeDate").descending());
        Page<Post> all = postRepository.findAll(pageable);
        result.setResponseCode(all.isEmpty() ? ResponseCode.DB_NOT_FOUND_DATA : result.getResponseCode());
        result.setList(all);
        return result;
    };

    public PageResult listByLocation(SearchParam searchParam) {
        //결과
        PageResult result = new PageResult(ResponseCode.SUCCESS);
        Pageable pageable = PageRequest.of(searchParam.getPage() - 1, searchParam.getSize(), Sort.by("writeDate").descending());
        Page<Post> all = postRepository.findAll(PostSpecifications.equalToSpecificLocation(searchParam.getLocation())
                                                .and(PostSpecifications.findNotDeclared())
                                                .and(PostSpecifications.findAllTodayPosts(LocalDateTime.now())), pageable);
        result.setResponseCode(all.isEmpty() ? ResponseCode.DB_NOT_FOUND_DATA : result.getResponseCode());
        result.setList(all);
        return result;
    }

    public BaseResult cancelDeclare(int id) {
        //결과
        BaseResult result = new BaseResult(ResponseCode.SUCCESS);
        Optional<Post> postById = postRepository.findById(id);
        if (postById.isPresent()) {
            Post post = postById.get();
            post.setDeclared_yn(Declare.NOT_DECLARED.getCode());
            postRepository.saveAndFlush(post);
        } else {
            result.setResponseCode(ResponseCode.DB_NOT_FOUND_DATA);
            log.info("[{}] {}",
                    Thread.currentThread().getStackTrace()[1].getMethodName(), result.getResponseCode().getMessage());
        }
        return result;
    }

    @Override
    public BaseResult add(Post post) {
        postRepository.save(post);
        return new BaseResult(ResponseCode.SUCCESS);
    }

    @Override
    public BaseResult recommend(int id) {
        //결과
        BaseResult result = new BaseResult(ResponseCode.SUCCESS);
        Optional<Post> postById = postRepository.findById(id);
        if (postById.isPresent()) {
            Post post = postById.get();
            post.setRecommendCnt(post.getRecommendCnt() + 1);
            postRepository.saveAndFlush(post);
        } else {
            result.setResponseCode(ResponseCode.DB_NOT_FOUND_DATA);
        }
        return result;
    }

    @Override
    public BaseResult declare(int id) {
        //결과
        BaseResult result = new BaseResult(ResponseCode.SUCCESS);
        Optional<Post> postById = postRepository.findById(id);
        if (postById.isPresent()) {
            Post post = postById.get();
            post.setDeclared_yn(Declare.DECLARED.getCode());
            postRepository.saveAndFlush(post);
        } else {
            result.setResponseCode(ResponseCode.DB_NOT_FOUND_DATA);
        }
        return result;
    }

    public BaseResult deletePost(int id) {
        postRepository.deleteById(id);
        return new BaseResult(ResponseCode.SUCCESS);
    }
}
