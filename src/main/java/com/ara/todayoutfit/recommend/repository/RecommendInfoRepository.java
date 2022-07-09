package com.ara.todayoutfit.recommend.repository;

import com.ara.todayoutfit.recommend.model.RecommendInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendInfoRepository extends JpaRepository<RecommendInfo, Integer>, JpaSpecificationExecutor<RecommendInfo> {

    @Override
    List<RecommendInfo> findAll();
}
