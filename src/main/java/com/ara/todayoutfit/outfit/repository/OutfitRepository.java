package com.ara.todayoutfit.outfit.repository;

import com.ara.todayoutfit.outfit.domain.Outfit;
import com.ara.todayoutfit.outfit.request.OutfitUpdateRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.ara.todayoutfit.outfit.domain.QOutfit.outfit;

@Repository
@Transactional
public class OutfitRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public OutfitRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<Outfit> findOutfitById(long id) {
        Outfit outfit = em.find(Outfit.class, id);
        return Optional.ofNullable(outfit);
    }

    public Outfit saveOutfit(Outfit outfit) {
        em.persist(outfit);
        return outfit;
    }

    public List<Outfit> findOutfitsByTemperature(int temperature) {
        return queryFactory.selectFrom(outfit)
                .where(betweenMaxTempAndMinTemp(temperature))
                .fetch();
    }

    private BooleanExpression betweenMaxTempAndMinTemp(Integer temp) {
        return outfit.minTemperature.loe(temp).and(outfit.maxTemperature.goe(temp));
    }

    public void deleteOutfitById(Long id) {
        queryFactory.delete(outfit)
                .where(outfit.outfitId.eq(id))
                .execute();
    }

    public void deleteAll() {
        queryFactory.delete(outfit)
                .execute();
    }
}
