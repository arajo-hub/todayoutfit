package com.ara.todayoutfit.recommend.repository;

import com.ara.todayoutfit.recommend.model.RecommendInfo;
import com.ara.todayoutfit.recommend.model.RecommendInfoUpdate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.criteria.internal.predicate.BooleanExpressionPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.ara.todayoutfit.recommend.model.QRecommendInfo.recommendInfo;

@Slf4j
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

    public RecommendInfo save(RecommendInfo recommendInfo) {
        em.persist(recommendInfo);
        return recommendInfo;
    }

    public Optional<RecommendInfo> findBySeq(Long recommendInfoSeq) {
        RecommendInfo recommendInfo = em.find(RecommendInfo.class, recommendInfoSeq);
        log.info(String.valueOf(recommendInfo));
        return Optional.ofNullable(recommendInfo);
    }

    public void deleteBySeq(Long seq) {
        queryFactory.delete(recommendInfo)
                .where(equalsSeq(seq))
                .execute();
    }

    public void deleteAll() {
        queryFactory.delete(recommendInfo)
                .execute();
    }

    private BooleanExpression equalsSeq(Long seq) {
        return recommendInfo.recommendInfoSeq.eq(seq);
    }

    public void update(Long seq, RecommendInfoUpdate recommendInfoUpdate) {
        queryFactory.update(recommendInfo)
                .set(recommendInfo.maxTemp, recommendInfoUpdate.getMaxTemp())
                .set(recommendInfo.minTemp, recommendInfoUpdate.getMinTemp())
                .set(recommendInfo.message, recommendInfoUpdate.getMessage())
                .where(equalsSeq(seq))
                .execute();
    }

}
