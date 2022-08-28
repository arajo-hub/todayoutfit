package com.ara.todayoutfit.member.repository;

import com.ara.todayoutfit.member.model.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ara.todayoutfit.member.model.Member;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.ara.todayoutfit.member.model.QMember.member;

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

    public void deleteById(String id) {
        queryFactory.delete(member)
                .where(equalsId(id))
                .execute();
    }

    private BooleanExpression equalsId(String id) {
        return member.id.eq(id);
    }

    public void deleteAll() {
        queryFactory.delete(member)
                .execute();
    }

    public Optional<Member> findById(String id) {
        Member user = em.find(Member.class, id);
        return Optional.ofNullable(user);
    }

    public List<Member> findAll() {
        return queryFactory.selectFrom(member)
                .fetch();
    }

}
