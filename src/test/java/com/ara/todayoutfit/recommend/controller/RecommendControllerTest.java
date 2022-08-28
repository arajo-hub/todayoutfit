package com.ara.todayoutfit.recommend.controller;

import com.ara.todayoutfit.recommend.model.RecommendInfo;
import com.ara.todayoutfit.recommend.repository.RecommendInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class RecommendControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private RecommendInfoRepository recommendInfoRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @BeforeEach
    void clean() {
        recommendInfoRepository.deleteAll();
    }

    @Test
    @DisplayName("기온에 따른 추천정보 가져오기")
    void recommend() throws Exception {
        RecommendInfo recommendInfo = RecommendInfo.builder()
                .maxTemp(40)
                .minTemp(21)
                .message("추천정보1입니다.").build();
        recommendInfoRepository.saveAll(List.of(recommendInfo,
                RecommendInfo.builder()
                        .maxTemp(20)
                        .minTemp(5)
                        .message("추천정보2입니다.").build()));

        String temp = Integer.toString(35);

        mockMvc.perform(get("/recommend")
                        .param("temp", temp))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temp").value(recommendInfo.getMessage()))
                .andDo(MockMvcResultHandlers.print());
    }
}