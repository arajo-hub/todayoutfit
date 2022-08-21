package com.ara.todayoutfit.recommend.repository;

import com.ara.todayoutfit.recommend.model.RecommendInfo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ara.todayoutfit.recommend.model.QRecommendInfo.recommendInfo;

@Repository
@Transactional
public class RecommendInfoRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public RecommendInfoRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<RecommendInfo> findAll() {
        return queryFactory.selectFrom(recommendInfo).fetch();
    }

    public List<RecommendInfo> findRecommendInfoByTemp(Integer temp) {
        return queryFactory.selectFrom(recommendInfo)
                .where(betweenMaxTempAndMinTemp(temp))
                .orderBy(recommendInfo.minTemp.asc())
                .fetch();
    }

    private BooleanExpression betweenMaxTempAndMinTemp(Integer temp) {
        return recommendInfo.minTemp.loe(temp).and(recommendInfo.maxTemp.goe(temp));
    }

    public List<RecommendInfo> saveAll(List<RecommendInfo> infos) {
        for (RecommendInfo info : infos) {
            em.persist(info);
        }
        return infos;
    }

}
