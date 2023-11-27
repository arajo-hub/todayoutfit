package com.ara.todayoutfit.post.repository;

import com.ara.todayoutfit.post.domain.PostLike;
import com.ara.todayoutfit.post.request.PostLikeMultiSearch;
import com.ara.todayoutfit.post.request.PostLikeSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostLikeRepository {

    private final EntityManager em;

    public PostLike insertPostLike(PostLike postLike) {
        em.persist(postLike);
        return postLike;
    }

    public List<Long> getPostLikes(PostLikeMultiSearch search) {
        return em.createQuery("select pl.postSeq from PostLike pl where pl.postSeq IN :postIds and pl.ip = :ip", Long.class)
                .setParameter("postIds", search.getPostIds())
                .setParameter("ip", search.getIp())
                .getResultList();
    }

    public int deletePostLike(PostLikeSearch search) {
        return em.createQuery("delete from PostLike pl where pl.postSeq = :postId and pl.ip = :ip")
                .setParameter("postId", search.getPostId())
                .setParameter("ip", search.getIp())
                .executeUpdate();
    }
}
