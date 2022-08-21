package com.ara.todayoutfit.recommend.service;

import com.ara.todayoutfit.recommend.model.RecommendInfo;
import com.ara.todayoutfit.recommend.repository.RecommendInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class RecommendInfoServiceTest {

    @Autowired
    private RecommendInfoService recommendInfoService;

    @Autowired
    private RecommendInfoRepository recommendInfoRepository;

    @Test
    @DisplayName("추천정보 전체 조회")
    void getAllRecommendInfo() {
        recommendInfoRepository.saveAll(List.of(RecommendInfo.builder()
                        .maxTemp(20)
                        .minTemp(5)
                        .message("추천정보1입니다.").build(),
                        RecommendInfo.builder()
                        .maxTemp(20)
                        .minTemp(5)
                        .message("추천정보2입니다.").build()));
        List<RecommendInfo> all = recommendInfoRepository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("기온에 따라 추천정보 조회")
    void getRecommendInfoByTemp() {
        RecommendInfo recommendInfo = RecommendInfo.builder()
                .maxTemp(40)
                .minTemp(21)
                .message("추천정보1입니다.").build();
        recommendInfoRepository.saveAll(List.of(recommendInfo,
                RecommendInfo.builder()
                        .maxTemp(20)
                        .minTemp(5)
                        .message("추천정보2입니다.").build()));
        int temp = 37;
        RecommendInfo recommendInfoByTemp = recommendInfoService.getRecommendInfoByTemp(temp);
        assertEquals(recommendInfo, recommendInfoByTemp);
    }

}