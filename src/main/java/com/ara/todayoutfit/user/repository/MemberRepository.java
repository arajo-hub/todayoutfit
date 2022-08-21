package com.ara.todayoutfit.user.repository;

import com.ara.todayoutfit.user.model.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@Transactional
public class MemberRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MemberRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Member save(Member user) {
        em.persist(user);
        return user;
    }

    public void delete(Member user) {
        em.remove(user);
    }

    public Optional<Member> findById(String id) {
        Member user = em.find(Member.class, id);
        return Optional.ofNullable(user);
    }

}
