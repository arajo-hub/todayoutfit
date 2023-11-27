package com.ara.todayoutfit.recommendInfo.service;

import com.ara.todayoutfit.common.ObjectResponse;
import com.ara.todayoutfit.recommendInfo.model.RecommendInfo;
import com.ara.todayoutfit.recommendInfo.model.RecommendInfoUpdate;
import com.ara.todayoutfit.recommendInfo.repository.RecommendInfoRepository;
import com.ara.todayoutfit.recommendInfo.request.RecommendInfoCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class RecommendInfoServiceTest {

    @Autowired
    private RecommendInfoService recommendInfoService;

    @Autowired
    private RecommendInfoRepository recommendInfoRepository;

    @BeforeEach
    void clean() {
        recommendInfoRepository.deleteAll();
    }

//    @Test
//    @DisplayName("추천정보 전체 조회")
//    void getAllRecommendInfo() {
//        recommendInfoRepository.saveAll(List.of(RecommendInfo.builder()
//                        .maxTemp(20)
//                        .minTemp(5)
//                        .message("추천정보1입니다.").build(),
//                        RecommendInfo.builder()
//                        .maxTemp(20)
//                        .minTemp(5)
//                        .message("추천정보2입니다.").build()));
//        List<RecommendInfo> all = recommendInfoRepository.findAll();
//        assertEquals(2, all.size());
//    }
//
    @Test
    @DisplayName("기온에 따라 추천정보 조회")
    void getRecommendInfoByTemperature() {
        RecommendInfo recommendInfo = RecommendInfo.builder()
                .maxTemp(40)
                .minTemp(21)
                .message("추천정보1입니다.").build();
        recommendInfoRepository.saveRecommendInfo(recommendInfo);
        int temp = 37;
        ObjectResponse recommendInfoByTemp = recommendInfoService.findRecommendInfoByTemperature(temp);
        assertNotNull(recommendInfoByTemp.getObject());
    }

    @Test
    @DisplayName("추천정보 추가")
    void save() {
        RecommendInfoCreateRequest recommendInfo = RecommendInfoCreateRequest.builder()
                .maxTemperature(40)
                .minTemperature(20)
                .message("추가 테스트입니다.")
                .build();

        recommendInfoService.saveRecommendInfo(recommendInfo);

        Optional<RecommendInfo> recommendInfoBySeq = recommendInfoRepository.findBySeq(1L);

        assertTrue(recommendInfoBySeq.isPresent());
        assertEquals(recommendInfo.getMessage(), recommendInfoBySeq.get().getMessage());
    }

    @Test
    @DisplayName("추천정보 삭제")
    void delete() {
        RecommendInfoCreateRequest recommendInfo = RecommendInfoCreateRequest.builder()
                .maxTemperature(40)
                .minTemperature(20)
                .message("추가 테스트입니다.")
                .build();

        recommendInfoService.saveRecommendInfo(recommendInfo);

//        recommendInfoService.delete(recommendInfo.getRecommendInfoSeq());

        int size = recommendInfoRepository.findAll().size();

        assertEquals(0, size);
    }

    @Test
    @DisplayName("추천정보 수정")
    void update() {
        RecommendInfoCreateRequest recommendInfo = RecommendInfoCreateRequest.builder()
                .maxTemperature(40)
                .minTemperature(20)
                .message("추가 테스트입니다.")
                .build();

        recommendInfoService.saveRecommendInfo(recommendInfo);

        RecommendInfoUpdate recommendInfoUpdate = RecommendInfoUpdate.builder()
                .maxTemp(50)
                .minTemp(10)
                .message("수정 메시지입니다.")
                .build();

//        recommendInfoService.update(recommendInfo.getRecommendInfoSeq(), recommendInfoUpdate);

//        Optional<RecommendInfo> updatedBySeq = recommendInfoRepository.findBySeq(recommendInfo.getRecommendInfoSeq());

//        RecommendInfo updated = new RecommendInfo();
//        if (updatedBySeq.isPresent()) {
//            updated = updatedBySeq.get();
//        }
        log.info(recommendInfo.toString());
//        assertNotEquals(recommendInfo, updated);
    }

}