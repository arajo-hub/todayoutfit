package com.ara.todayoutfit.board.service;

import com.ara.todayoutfit.board.model.Post;
import com.ara.todayoutfit.board.model.PostSearch;
import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.PageResult;

import java.util.List;

public interface PostService {

    PageResult findAll(PostSearch postSearch);

    PageResult findByLocation(PostSearch postSearch);

    BaseResult delete(Long seq);

    BaseResult deleteAll();

    BaseResult cancelDeclare(Long seq);

    BaseResult save(Post post);

    BaseResult saveAll(List<Post> posts);

    BaseResult recommend(Long seq, String ip);

    BaseResult declare(Long seq);

}
