package com.ara.todayoutfit.recommend.repository;

import com.ara.todayoutfit.recommend.model.RecommendInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendInfoRepository extends JpaRepository<RecommendInfo, Integer>, JpaSpecificationExecutor<RecommendInfo> {

    List<RecommendInfo> findAll();

    @Query(value = "select recommendinfo_seq, max_temp, min_temp, message from recommendinfo where max_temp >= :temp and min_temp <= :temp order by min_temp asc", nativeQuery = true)
    List<RecommendInfo> findRecommendInfoByTemp(@Param(value = "temp") double temp);

}
