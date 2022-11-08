package com.ara.todayoutfit.recommend.repository;

import com.ara.todayoutfit.recommend.model.RecommendInfo;
import com.ara.todayoutfit.recommend.model.RecommendInfoShow;
import com.ara.todayoutfit.recommend.model.RecommendInfoUpdate;
import com.ara.todayoutfit.recommend.model.RecommendInfoSearch;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
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

    public Page<RecommendInfoShow> findAll(RecommendInfoSearch recommendSearch) {
        Pageable pageable = PageRequest.of(recommendSearch.getPage() - 1, recommendSearch.getSize());
        List<RecommendInfo> recommendInfos = queryFactory.selectFrom(recommendInfo)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(recommendInfo.recommendInfoSeq.desc())
                .fetch();
        Long count = queryFactory.select(recommendInfo.count())
                .from(recommendInfo)
                .fetchOne();
        List<RecommendInfoShow> recommendShows = new ArrayList<RecommendInfoShow>();
        for (RecommendInfo recommendInfo : recommendInfos) {
            recommendShows.add(recommendInfo.toRecommendInfoShow().build());
        }
        return new PageImpl<>(recommendShows, pageable, count);
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
