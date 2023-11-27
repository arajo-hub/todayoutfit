package com.ara.todayoutfit.post.repository;

import com.ara.todayoutfit.post.domain.Post;
import com.ara.todayoutfit.post.response.*;
import com.ara.todayoutfit.post.request.PostSearch;
import com.ara.todayoutfit.post.response.PostShow;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ara.todayoutfit.post.domain.QPost.post;

@Repository
@Transactional
public class PostRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PostRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<Post> findBySeq(Long seq) {
        Post post = em.find(Post.class, seq);
        return Optional.ofNullable(post);
    }

    public List<Post> findPostByLocation(PostSearch postSearch, Pageable pageable) {
        return queryFactory.selectFrom(post)
                .where(likeLocation(postSearch.getLocation()), writeToday(), notDeclared())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.writeDate.desc())
                .fetch();
    }

    public Long getCount(String location) {
        return queryFactory.select(post.count())
                .from(post)
                .where(likeLocation(location), writeToday(), notDeclared())
                .fetchOne();
    }

    private BooleanExpression likeLocation(String location) {
        return StringUtils.hasText(location) ? post.location.contains(location) : null;
    }

    private BooleanExpression writeToday() {
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59));
        return post.writeDate.between(todayStart, todayEnd);
    }

    private BooleanExpression notDeclared() {
        return post.declaredYn.eq(false);
    }

    public List<Post> findAll(PostSearch postSearch, Pageable pageable) {
        return queryFactory.selectFrom(post)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.writeDate.desc())
                .fetch();
    }

    public List<Post> findAll() {
        return queryFactory.selectFrom(post)
                .fetch();
    }

    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    public List<Post> saveAll(List<Post> posts) {
        for (Post post : posts) {
            em.persist(post);
        }
        return posts;
    }

    public void deleteBySeq(Long seq) {
        queryFactory.delete(post)
                .where(equalsSeq(seq)).execute();
    }

    private BooleanExpression equalsSeq(Long seq) {
        return post.postId.eq(seq);
    }

    public void deleteAll() {
        queryFactory.delete(post)
                .execute();
    }

    public void cancelDeclare(Long seq) {
        queryFactory.update(post)
                .set(post.declaredYn, false)
                .where(equalsSeq(seq))
                .execute();
    }

    public void declare(Long seq) {
        queryFactory.update(post)
                .set(post.declaredYn, true)
                .where(equalsSeq(seq))
                .execute();
    }

    public void recommend(Long seq) {
        queryFactory.update(post)
                .set(post.recommendCnt, post.recommendCnt.add(1))
                .where(equalsSeq(seq))
                .execute();
    }

    public void cancelRecommend(Long seq) {
        queryFactory.update(post)
                .set(post.recommendCnt, post.recommendCnt.add(-1))
                .where(equalsSeq(seq))
                .execute();
    }

}
