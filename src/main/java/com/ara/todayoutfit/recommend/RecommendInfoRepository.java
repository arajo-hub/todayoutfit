package com.ara.todayoutfit.recommend;

import com.ara.todayoutfit.board.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendInfoRepository extends JpaRepository<RecommendInfo, Integer>, JpaSpecificationExecutor<RecommendInfo> {

    @Override
    List<RecommendInfo> findAll();
}
