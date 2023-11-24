package com.ara.todayoutfit.post.service;

import com.ara.todayoutfit.post.domain.PostLike;

public interface PostLikeService {

    void save(PostLike postLike);

    boolean isAlreadyLiked(Long postSeq, String ip);

    void deleteSamePostSeqAdnIp(Long postSeq, String ip);

}
