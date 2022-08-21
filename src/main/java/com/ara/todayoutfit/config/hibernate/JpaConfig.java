package com.ara.todayoutfit.config.hibernate;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class JpaConfig {

    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public JpaConfig(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

}
