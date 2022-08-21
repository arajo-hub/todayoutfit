package com.ara.todayoutfit.board.repository;

import com.ara.todayoutfit.board.model.*;
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

import static com.ara.todayoutfit.board.model.QPost.post;

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

    public Page<PostShow> findByLocation(PostSearch postSearch) {
        Pageable pageable = PageRequest.of(postSearch.getPage() - 1, postSearch.getSize());
        List<Post> posts = queryFactory.selectFrom(post)
                .where(likeLocation(postSearch.getLocation()), writeToday(), notDeclared())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.writeDate.desc())
                .fetch();
        Long count = getCount(postSearch.getLocation());
        List<PostShow> postShows = new ArrayList<PostShow>();
        for (Post post : posts) {
            postShows.add(post.toPostShow().build());
        }
        return new PageImpl<>(postShows, pageable, count);
    }

    private Long getCount(String location) {
        return queryFactory.select(post.count())
                .from(post)
                .where(likeLocation(location), writeToday(), notDeclared())
                .fetchOne();
    }

    private BooleanExpression likeLocation(String location) {
        return StringUtils.hasText(location) ? post.location.contains(location) : null;
    }

    private BooleanExpression writeToday() {
        LocalDateTime yesterday = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0, 0));
        LocalDateTime tomorrow = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0, 0));
        return post.writeDate.between(yesterday, tomorrow);
    }

    private BooleanExpression notDeclared() {
        return post.declaredYn.eq(false);
    }

    public Page<PostShow> findAll(PostSearch postSearch) {
        Pageable pageable = PageRequest.of(postSearch.getPage() - 1, postSearch.getSize());
        List<Post> posts = queryFactory.selectFrom(post)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.writeDate.desc())
                .fetch();
        Long count = queryFactory.select(post.count())
                .from(post)
                .fetchOne();
        List<PostShow> postShows = new ArrayList<PostShow>();
        for (Post post : posts) {
            postShows.add(post.toPostShow().build());
        }
        return new PageImpl<>(postShows, pageable, count);
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

    public void delete(Post post) {
        em.remove(post);
    }

    private BooleanExpression equalsSeq(Long seq) {
        return post.postSeq.eq(seq);
    }

    public void deleteAll() {
        queryFactory.delete(post)
                .execute();
    }

}
