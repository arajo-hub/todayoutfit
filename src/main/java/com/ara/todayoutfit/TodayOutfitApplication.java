package com.ara.todayoutfit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@Slf4j
@EnableCaching
@SpringBootApplication
public class TodayOutfitApplication {

    public static void main(String[] args) {
        log.info("TodayOutfitApplication.main");
        SpringApplication.run(TodayOutfitApplication.class, args);
    }
}
