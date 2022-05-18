package com.ara.todayoutfit.board;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.PageResult;
import com.ara.todayoutfit.common.SearchParam;

public interface PostService {
    PageResult list(SearchParam searchParam);

    PageResult listByLocation(SearchParam searchParam);

    BaseResult deletePost(int parseInt);

    BaseResult cancelDeclare(int parseInt);

    BaseResult add(Post post);

    BaseResult recommend(int id);

    BaseResult declare(int id);

}
