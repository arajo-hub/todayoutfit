package com.ara.todayoutfit.board.repository;

import com.ara.todayoutfit.board.model.PostLike;
import com.ara.todayoutfit.board.model.QPostLike;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static com.ara.todayoutfit.board.model.QPostLike.postLike;

@Repository
@Transactional
public class PostLikeRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PostLikeRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<PostLike> findBySeq(Long seq) {
        PostLike postLike = em.find(PostLike.class, seq);
        return Optional.ofNullable(postLike);
    }

    public PostLike save(PostLike postLike) {
        em.persist(postLike);
        return postLike;
    }

    public boolean isAlreadyLiked(Long seq, String ip) {
        Long cnt = queryFactory.select(postLike.postLikeSeq.count())
                .from(postLike)
                .where(isSameIp(ip)).fetchOne();
        return (cnt != 0) ? true : false;
    }

    private BooleanExpression isSameIp(String ip) {
        return postLike.ip.eq(ip);
    }

}
