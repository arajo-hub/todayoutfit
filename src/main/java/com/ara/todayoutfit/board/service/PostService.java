package com.ara.todayoutfit.board.service;

import com.ara.todayoutfit.board.model.Post;
import com.ara.todayoutfit.board.model.PostSearch;
import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.PageResult;
import com.ara.todayoutfit.common.SearchParam;

import java.util.List;
import java.util.Optional;

public interface PostService {

    PageResult findAll(SearchParam searchParam);

    PageResult findByLocation(SearchParam searchParam);

    BaseResult delete(Long seq);

    BaseResult deleteAll();

    BaseResult cancelDeclare(Long seq);

    BaseResult save(Post post);

    BaseResult saveAll(List<Post> posts);

    BaseResult recommend(Long seq);

    BaseResult declare(Long seq);

}
