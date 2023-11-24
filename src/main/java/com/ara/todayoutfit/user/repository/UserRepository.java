package com.ara.todayoutfit.user.repository;

import com.ara.todayoutfit.user.domain.QUser;
import com.ara.todayoutfit.user.domain.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.ara.todayoutfit.user.domain.QUser.user;

@Repository
@Transactional
public class UserRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public UserRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public User save(User user) {
        em.persist(user);
        return user;
    }

    public void deleteById(String id) {
        queryFactory.delete(user)
                .where(equalsId(id))
                .execute();
    }

    private BooleanExpression equalsId(String id) {
        return user.id.eq(id);
    }

    public void deleteAll() {
        queryFactory.delete(user)
                .execute();
    }

    public Optional<User> findById(String id) {
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }

    public List<User> findAll() {
        return queryFactory.selectFrom(user)
                .fetch();
    }

}
