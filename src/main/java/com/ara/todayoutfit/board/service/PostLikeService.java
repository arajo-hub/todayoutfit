package com.ara.todayoutfit.board.service;

import com.ara.todayoutfit.board.model.PostLike;

public interface PostLikeService {

    void save(PostLike postLike);

    boolean isAlreadyLiked(Long postSeq, String ip);

    void deleteSamePostSeqAdnIp(Long postSeq, String ip);

}
