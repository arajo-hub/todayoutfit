package com.ara.todayoutfit.board.service.impl;

import com.ara.todayoutfit.board.model.PostLike;
import com.ara.todayoutfit.board.repository.PostLikeRepository;
import com.ara.todayoutfit.board.service.PostLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostLikeServiceImpl implements PostLikeService {

    @Autowired
    private PostLikeRepository postLikeRepository;

    public void save(PostLike postLike) {
        postLikeRepository.save(postLike);
    }

    @Override
    public boolean isAlreadyLiked(Long postSeq, String ip) {
        return postLikeRepository.isAlreadyLiked(postSeq, ip);
    }

    @Override
    public void deleteSamePostSeqAdnIp(Long seq, String ip) {
        postLikeRepository.deleteSamePostSeqAdnIp(seq, ip);
    }

}